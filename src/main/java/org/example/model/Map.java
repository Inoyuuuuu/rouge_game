package org.example.model;

import java.util.LinkedList;
import java.util.Vector;
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
                cells[i][j].setContent(chamberCharacter);
                cells[i][j].setCellType(CellType.CHAMBER);

                if (i == posX || i == posX + sizeX - 1 || j == posY || j == posY + sizeY - 1) {
                    cells[i][j].setContent(wallCharacter);
                    cells[i][j].setCellType(CellType.WALL);
                }
            }
        }
    }

    public void initRandomRectangles() {
        int amountOfChambers = ThreadLocalRandom.current().nextInt(8, 10);
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
        Chamber currentChamber;

        for (int i = 0; i < chambers.size(); i++) {
            currentChamber = chambers.get(i);

            int[] topMidOfChamber = new int[2];
            topMidOfChamber[0] = currentChamber.getPositionX() + currentChamber.getWidth() / 2;
            topMidOfChamber[1] = currentChamber.getPositionY();

            int[] botMidOfChamber = new int[2];
            botMidOfChamber[0] = currentChamber.getPositionX() + currentChamber.getWidth() / 2;
            botMidOfChamber[1] = currentChamber.getPositionY() + currentChamber.getHeight() - 1;

            int[] rightMidOfChamber = new int[2];
            rightMidOfChamber[0] = currentChamber.getPositionX() + currentChamber.getWidth() - 1;
            rightMidOfChamber[1] = currentChamber.getPositionY() + currentChamber.getHeight() / 2;

            int[] leftMidOfChamber = new int[2];
            leftMidOfChamber[0] = currentChamber.getPositionX();
            leftMidOfChamber[1] = currentChamber.getPositionY() + currentChamber.getHeight() / 2;

            cells[topMidOfChamber[0]][topMidOfChamber[1]].setCellType(CellType.DOOR);
            cells[botMidOfChamber[0]][botMidOfChamber[1]].setCellType(CellType.DOOR);
            cells[rightMidOfChamber[0]][rightMidOfChamber[1]].setCellType(CellType.DOOR);
            cells[leftMidOfChamber[0]][leftMidOfChamber[1]].setCellType(CellType.DOOR);

            for (int k = topMidOfChamber[1] - 1; k >= 0; k--) {
                if (cells[topMidOfChamber[0]][k].getBelongsToChamberNumber() == 0) {
                    cells[topMidOfChamber[0]][k].setCellType(CellType.PATH);
                    if (k == 0) {
                        for (int j = k; j < topMidOfChamber[1]; j++) {
                            cells[topMidOfChamber[0]][j].setCellType(CellType.BACKGROUND);
                        }
                    }
                } else {
                    if (!getChamberByChamberNumber(cells[topMidOfChamber[0]][k].getBelongsToChamberNumber()).isConnected()) {
                        chambers.get(i).setConnected(true);
                        getChamberByChamberNumber(cells[topMidOfChamber[0]][k].getBelongsToChamberNumber()).setConnected(true);
                    } else {
                        for (int j = k + 1; j < topMidOfChamber[1]; j++) {
                            cells[topMidOfChamber[0]][j].setCellType(CellType.BACKGROUND);
                        }
                    }
                    break;
                }
            }

           for (int k = botMidOfChamber[1] + 1; k < height; k++) {
                if (cells[botMidOfChamber[0]][k].getBelongsToChamberNumber() == 0) {
                    cells[botMidOfChamber[0]][k].setCellType(CellType.PATH);

                    if (k == height - 1) {
                        for (int j = k; j > botMidOfChamber[1]; j--) {
                            cells[botMidOfChamber[0]][j].setCellType(CellType.BACKGROUND);
                        }
                    }
                } else {
                    if (!getChamberByChamberNumber(cells[botMidOfChamber[0]][k].getBelongsToChamberNumber()).isConnected()) {
                        chambers.get(i).setConnected(true);
                        getChamberByChamberNumber(cells[botMidOfChamber[0]][k].getBelongsToChamberNumber()).setConnected(true);
                    } else {
                        for (int j = k - 1; j > botMidOfChamber[1]; j--) {
                            cells[botMidOfChamber[0]][j].setCellType(CellType.BACKGROUND);
                        }
                    }
                    break;
                }
            }

            /*for (int k = 0; k < leftMidOfChamber[0]; k++) {
                if (cells[k][leftMidOfChamber[1]].getBelongsToChamberNumber() == 0) {
                    cells[k][leftMidOfChamber[1]].setCellType(CellType.PATH);
                }
            }

            for (int k = width - 1; k > rightMidOfChamber[0]; k--) {
                if (cells[k][rightMidOfChamber[1]].getBelongsToChamberNumber() == 0) {
                    cells[k][rightMidOfChamber[1]].setCellType(CellType.PATH);
                }
            }*/
        }
        System.out.println(chambers.size());
    }

    private void drawPathColumn(int startY, int targetX, int targetY, Chamber currentChamber, Cell targetCell) {

    }

    public void initStartChamber(Player player) {
        chambers.add(new Chamber(player.getPositionX() - 5, player.getPositionY() - 5,
                10, 10));

        drawChamber(EMPTY_BACKGROUND, WALL_SYMBOL,player.getPositionX() - 5, player.getPositionY() - 5,
                10, 10, chambers.getLast().getChamberNumber());

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cells[i + player.getPositionX() - 5][j + player.getPositionY() - 5].getCelltype() != CellType.WALL) {
                    cells[i + player.getPositionX() - 5][j + player.getPositionY() - 5].setCellType(CellType.START_AREA);
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
