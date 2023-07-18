package org.example.model;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Map {
    private static final char EMPTY_BACKGROUND = ' ';
    private static final char WALL_SYMBOL = '#';
    private final int height;
    private final int width;
    private final Cell[][] cells;
    LinkedList<Chamber> chambers = new LinkedList<>();

    public Map(int width, int height) {
        this.height = height;
        this.width = width;
        this.cells = new Cell[width][height];
        initMap();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void initMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                 this.cells[i][j] = new Cell(EMPTY_BACKGROUND, CellType.BORDER);
            }
        }
    }

    private void drawRectangle(char chamberCharacter, char wallCharacter, int posX, int posY, int sizeX, int sizeY) {
        for (int i = posX; i < posX + sizeX; i++) {
            for (int j = posY; j < posY + sizeY; j++) {

                if (i == posX || i == posX + sizeX - 1 || j == posY || j == posY + sizeY - 1) {
                    cells[i][j].setContent(wallCharacter);
                    cells[i][j].setCelltype(CellType.WALL);
                } else {
                    cells[i][j].setContent(chamberCharacter);
                    cells[i][j].setCelltype(CellType.CHAMBER);
                }
            }
        }
    }

    public void initRandomRectangles() {
        int amountOfRectangles = ThreadLocalRandom.current().nextInt(10, 15);
        int rectangleSizeX;
        int rectangleSizeY;
        int rectPosX;
        int rectPosY;
        int maximumAmountOfTries = 50;

        for (int i = 0; i < amountOfRectangles; i++) {
            int tries = 0;
            int rectangleDistance = 2;

            do {
                rectangleSizeX = ThreadLocalRandom.current().nextInt(8, 30);
                rectangleSizeY = ThreadLocalRandom.current().nextInt(8, 15);

                rectPosX  = ThreadLocalRandom.current().nextInt(rectangleDistance, 115 - rectangleSizeX - rectangleDistance);
                rectPosY  = ThreadLocalRandom.current().nextInt(rectangleDistance, 40 - rectangleSizeY - rectangleDistance);

                tries++;

            } while (isRectangleOverlapping(rectPosX, rectPosY, rectangleSizeX, rectangleSizeY, rectangleDistance)
                    && tries <= maximumAmountOfTries);

            if (tries <= maximumAmountOfTries) {
                drawRectangle(EMPTY_BACKGROUND, WALL_SYMBOL, rectPosX, rectPosY, rectangleSizeX, rectangleSizeY);
                chambers.add(new Chamber(rectPosX, rectPosY, rectangleSizeX, rectangleSizeY));

            } else {
                System.out.println("couldn't fit rectangle, tried " + maximumAmountOfTries + " times!");
            }
        }
    }

    public void initPaths() {
        int amountOfChambers = chambers.size();
        Chamber currentChamber;

        /*for (int i = 0; i < amountOfChambers; i++) {
            currentChamber = chambers.get(i);

            while(!currentChamber.isConnected()) {

                for (int k = 0; k < height - currentChamber.getPositionY(); k++) {
                    if (cells[currentChamber.getWidth() / 2][currentChamber.getPositionX() + k].getCelltype() == CellType.WALL) {
                        cells
                    }

                    cells[currentChamber.getWidth() / 2]
                            [currentChamber.getPositionY() + k].setCelltype(CellType.CHAMBER);
                     currentChamber.setConnected(true);
                }
            }
        }*/
    }

    public void initStartChamber(Player player) {
        drawRectangle(EMPTY_BACKGROUND, WALL_SYMBOL,player.getPositionX() - 5, player.getPositionY() - 5,
                10, 10);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cells[i + player.getPositionX() - 5][j + player.getPositionY() - 5].getCelltype() != CellType.WALL) {
                    cells[i + player.getPositionX() - 5][j + player.getPositionY() - 5].setCelltype(CellType.START_AREA);
                }
            }
        }
        chambers.add(new Chamber(player.getPositionX() - 5, player.getPositionY() - 5,
                10, 10));
    }

    private boolean isRectangleOverlapping(int posX, int posY, int sizeX, int sizeY, int rectangleDistance) {

        for (int i = -rectangleDistance; i < sizeX + rectangleDistance; i++) {
            for (int j = -rectangleDistance; j < sizeY + rectangleDistance; j++) {

                if (cells[i + posX][j + posY].getCelltype() == CellType.CHAMBER
                        || cells[i + posX][j + posY].getCelltype() == CellType.START_AREA) {
                    return true;
                }
            }
        }
        return false;
    }
}
