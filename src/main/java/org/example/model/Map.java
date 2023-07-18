package org.example.model;

import java.util.concurrent.ThreadLocalRandom;

public class Map {
    private static final char EMPTY_BACKGROUND = ' ';
    private static final char WALL_SYMBOL = '#';
    private final int height;
    private final int width;
    private final Cell[][] cells;

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
        int amountOfRectangles = ThreadLocalRandom.current().nextInt(8, 10);
        int rectangleSizeX;
        int rectangleSizeY;
        int rectPosX;
        int rectPosY;
        int maximumAmountOfTries = 20;

        for (int i = 0; i < amountOfRectangles; i++) {
            int tries = 0;

            do {
                rectangleSizeX = ThreadLocalRandom.current().nextInt(8, 30);
                rectangleSizeY = ThreadLocalRandom.current().nextInt(8, 20);

                rectPosX  = ThreadLocalRandom.current().nextInt(1, 115 - rectangleSizeX);
                rectPosY  = ThreadLocalRandom.current().nextInt(1, 40 - rectangleSizeY);

                tries++;

            } while (isRectangleOverlapping(rectPosX, rectPosY, rectangleSizeX, rectangleSizeY)
                    && tries <= maximumAmountOfTries);

            if (tries <= maximumAmountOfTries) {
                drawRectangle(EMPTY_BACKGROUND, WALL_SYMBOL, rectPosX, rectPosY, rectangleSizeX, rectangleSizeY);
            } else {
                System.out.println("couldn't fit rectangle, tried " + maximumAmountOfTries + " times!");
            }
        }
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
    }

    private boolean isRectangleOverlapping(int posX, int posY, int sizeX, int sizeY) {

        for (int i = -1; i < sizeX + 1; i++) {
            for (int j = -1; j < sizeY + 1; j++) {

                if (cells[i + posX][j + posY].getCelltype() == CellType.CHAMBER
                        || cells[i + posX][j + posY].getCelltype() == CellType.START_AREA) {
                    return true;
                }
            }
        }
        return false;
    }
}
