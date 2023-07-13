package org.example.model;

import java.util.concurrent.ThreadLocalRandom;

public class Map {
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
                 this.cells[i][j] = new Cell('#', CellType.BORDER);
            }
        }
    }

    private void drawRectangle(char character, int posX, int posY, int sizeX, int sizeY) {
        for (int i = posX; i < posX + sizeX; i++) {
            for (int j = posY; j < posY + sizeY; j++) {
                cells[i][j].setContent(character);
                cells[i][j].setCelltype(CellType.CHAMBER);
            }
        }
    }

    public void initRandomRectangles() {
        int amountOfRectangles = ThreadLocalRandom.current().nextInt(8, 10);
        int rectangleSizeX;
        int rectangleSizeY;
        int rectPosX;
        int rectPosY;
        int maximumAmountOfTries = 10;

        for (int i = 0; i < amountOfRectangles; i++) {
            int tries = 0;

            do {
                rectangleSizeX = ThreadLocalRandom.current().nextInt(5, 30);
                rectangleSizeY = ThreadLocalRandom.current().nextInt(5, 30);

                rectPosX  = ThreadLocalRandom.current().nextInt(1, 115 - rectangleSizeX);
                rectPosY  = ThreadLocalRandom.current().nextInt(1, 40 - rectangleSizeY);

                tries++;

            } while (isRectangleOverlapping(rectPosX, rectPosY, rectangleSizeX, rectangleSizeY)
                    && tries <= maximumAmountOfTries);

            if (tries <= maximumAmountOfTries) {
                drawRectangle(' ', rectPosX, rectPosY, rectangleSizeX, rectangleSizeY);
            } else {
                System.out.println("couldn't fit rectangle, tried " + maximumAmountOfTries + " times!");
            }
        }
    }

    public void initStartChamber(Player player) {
        drawRectangle(' ', player.getPlayerPosX() - 5, player.getPlayerPosY() - 5,
                10, 10);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i + player.getPlayerPosX() - 5][j + player.getPlayerPosY() - 5].setCelltype(CellType.START_AREA);
            }
        }
    }

    private boolean isRectangleOverlapping(int posX, int posY, int sizeX, int sizeY) {

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {

                if (cells[i + posX][j + posY].getCelltype() == CellType.CHAMBER
                        || cells[i + posX][j + posY].getCelltype() == CellType.START_AREA) {
                    return true;
                }
            }
        }
        return false;
    }
}
