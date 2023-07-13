package org.example.model;

import java.util.concurrent.ThreadLocalRandom;

public class Map {
    private final int height;
    private final int width;
    private final Cell[][] cells;

    public Map(int width, int height) {
        this.height = height;
        this.width = width;
        this.cells = new Cell[height][width];
        initMap();
        initRandomRectangles();
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
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                 this.cells[i][j] = new Cell('#');
            }
        }
    }

    private void drawRectangle(char character, int posX, int posY, int sizeX, int sizeY) {
        for (int i = posY; i < posY + sizeY; i++) {
            for (int j = posX; j < posX + sizeX; j++) {
                cells[i][j].setContent(character);
            }
        }
    }

    private void initRandomRectangles() {
        int amountOfRectangles = ThreadLocalRandom.current().nextInt(1, 7);

        for (int i = 0; i < amountOfRectangles; i++) {
            int rectangleX  = ThreadLocalRandom.current().nextInt(10, 95);
            int rectangleY  = ThreadLocalRandom.current().nextInt(10, 20);
            int rectangleSizeX = ThreadLocalRandom.current().nextInt(5, 20);
            int rectangleSizeY = ThreadLocalRandom.current().nextInt(5, 20);

            drawRectangle(' ', rectangleX, rectangleY, rectangleSizeX, rectangleSizeY);
        }
    }

    public void initStartChamber(Player player) {
        drawRectangle(' ', player.getPlayerPosX() - 5, player.getPlayerPosY() - 5, 10, 10);
    }

}
