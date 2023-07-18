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
        int amountOfChambers = ThreadLocalRandom.current().nextInt(10, 15);
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
        int counter;

        for (int i = 0; i < amountOfChambers; i++) {
            counter = 0;
            currentChamber = chambers.get(i);

            while(!currentChamber.isConnected()) {
                counter++;

                for (int k = 0; k < height - currentChamber.getPositionY() - 1; k++) {
                    Cell targetCell = cells[currentChamber.getPositionX() + currentChamber.getWidth() / 2][currentChamber.getPositionY() + 1 + k];

                    if (targetCell.getCelltype() == CellType.WALL) {
                        currentChamber.setConnected(true);
                        break;
                    }

                    targetCell.setCelltype(CellType.START_AREA);
                }

                for (int k = 0; k < width - currentChamber.getPositionX() - 1; k++) {
                    Cell targetCell = cells[currentChamber.getPositionX() + 1 + k][currentChamber.getPositionY() + currentChamber.getHeight() / 2];

                    if (targetCell.getCelltype() == CellType.WALL) {
                        currentChamber.setConnected(true);
                        break;
                    }

                    targetCell.setCelltype(CellType.START_AREA);
                }

                if (counter >= 500) {
                    return;
                }
            }
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
