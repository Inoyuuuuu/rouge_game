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
                 this.cells[i][j] = new Cell(EMPTY_BACKGROUND, CellType.BORDER, 0);
            }
        }
    }

    private void drawChamber(char chamberCharacter, char wallCharacter, int posX, int posY, int sizeX, int sizeY, int chamberNumber) {
        for (int i = posX; i < posX + sizeX; i++) {
            for (int j = posY; j < posY + sizeY; j++) {

                cells[i][j].setBelongsToChamberNumber(chamberNumber);

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
        int amountOfChambers = ThreadLocalRandom.current().nextInt(1, 2);
        int chamberSizeX;
        int chamberSizeY;
        int chamberPosX;
        int chamberPosY;
        int maximumAmountOfTries = 50;
        int chamberDistance = 2;

        for (int i = 0; i < amountOfChambers; i++) {
            int tries = 0;

            do {
                chamberSizeX = ThreadLocalRandom.current().nextInt(10, 30);
                chamberSizeY = ThreadLocalRandom.current().nextInt(10, 15);

                chamberPosX  = ThreadLocalRandom.current().nextInt(chamberDistance, 115 - chamberSizeX - chamberDistance);
                chamberPosY  = ThreadLocalRandom.current().nextInt(chamberDistance, 40 - chamberSizeY - chamberDistance);

                tries++;

            } while (isRectangleOverlapping(chamberPosX, chamberPosY, chamberSizeX, chamberSizeY, chamberDistance)
                    && tries <= maximumAmountOfTries);

            if (tries <= maximumAmountOfTries) {

                chambers.add(new Chamber(chamberPosX, chamberPosY, chamberSizeX, chamberSizeY));
                drawChamber(EMPTY_BACKGROUND, WALL_SYMBOL, chamberPosX, chamberPosY, chamberSizeX, chamberSizeY,
                        chambers.getLast().getChamberNumber());

            } else {
                System.out.println("couldn't fit rectangle, tried " + maximumAmountOfTries + " times!");
            }
        }
    }

    public void initPaths() {
        int amountOfChambers = chambers.size();
        Chamber currentChamber;
        int startX = Integer.MIN_VALUE;
        int startY = Integer.MIN_VALUE;
        int targetX = Integer.MIN_VALUE;
        int targetY = Integer.MIN_VALUE;

        for (int i = 0; i < amountOfChambers; i++) {
            currentChamber = chambers.get(i);

            //while(!currentChamber.isConnected()) {

                for (int k = 0; k < height; k++) {
                    Cell targetCell = cells[currentChamber.getPositionX() + currentChamber.getWidth() / 2][k];

                    if (targetCell.getBelongsToChamberNumber() == 0) {
                        targetCell.setCelltype(CellType.START_AREA);
                    }

                    if (targetCell.getCelltype() == CellType.WALL) {
                        if (targetCell.getBelongsToChamberNumber() != currentChamber.getChamberNumber()) {
                            currentChamber.setConnected(true);
                        } else {
                            targetCell.setCelltype(CellType.DOOR);
                        }
                    }
                }

                for (int k = 0; k < width - currentChamber.getWidth(); k++) {
                    Cell targetCell = cells[currentChamber.getWidth() + k][currentChamber.getPositionY() + currentChamber.getHeight() / 2];

                    if (targetCell.getBelongsToChamberNumber() == 0) {
                        targetCell.setCelltype(CellType.START_AREA);
                    }

                    if (targetCell.getCelltype() == CellType.WALL) {
                        if (targetCell.getBelongsToChamberNumber() != currentChamber.getChamberNumber()) {
                            currentChamber.setConnected(true);
                        } else {
                            targetCell.setCelltype(CellType.DOOR);
                        }
                    }
                }
            //drawPath(startX, startY, targetX, targetY);
        }
        //}
    }

    private void drawPath(int startX, int startY, int targetX, int targetY) {
        if (startX == Integer.MIN_VALUE || startY == Integer.MIN_VALUE
                || targetX  == Integer.MIN_VALUE || targetY == Integer.MIN_VALUE) {
            System.out.println("ERROR");
        } else {
        }
    }

    public void initStartChamber(Player player) {
        chambers.add(new Chamber(player.getPositionX() - 5, player.getPositionY() - 5,
                10, 10));

        drawChamber(EMPTY_BACKGROUND, WALL_SYMBOL,player.getPositionX() - 5, player.getPositionY() - 5,
                10, 10, chambers.getLast().getChamberNumber());

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cells[i + player.getPositionX() - 5][j + player.getPositionY() - 5].getCelltype() != CellType.WALL) {
                    cells[i + player.getPositionX() - 5][j + player.getPositionY() - 5].setCelltype(CellType.START_AREA);
                }
            }
        }
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

    private Chamber getChamberByChamberNumber(int number) {
        if (!chambers.isEmpty()) {
            for (Chamber chamber : chambers) {
                if (chamber.getChamberNumber() == number) {
                    return chamber;
                }
            }
        }
        return null;
    }
}
