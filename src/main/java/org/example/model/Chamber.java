package org.example.model;

public class Chamber {
    private static int chamberCounter = 1;
    private final int chamberNumber;
    private final int positionX;
    private final int positionY;
    private final int width;
    private final int height;
    private boolean isConnected;

    public Chamber(int positionX, int positionY, int width, int height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;

        this.isConnected = false;

        this.chamberNumber = chamberCounter;
        chamberCounter++;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getChamberNumber() {
        return chamberNumber;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
