package org.example.model;

public class Player {
    private final char playerSymbol;
    private int positionX;
    private int positionY;
    private int previousPositionX;
    private int previousPositionY;

    public Player(char playerSymbol, int initPlayerPosX, int initPlayerPosY) {
        this.playerSymbol = playerSymbol;
        this.positionX = initPlayerPosX;
        this.positionY = initPlayerPosY;

        this.previousPositionX = positionX;
        this.previousPositionY = positionY;
    }

    public char getPlayerSymbol() {
        return this.playerSymbol;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPreviousPositionX() {
        return previousPositionX;
    }

    public void setPreviousPositionX(int previousPositionX) {
        this.previousPositionX = previousPositionX;
    }

    public int getPreviousPositionY() {
        return previousPositionY;
    }

    public void setPreviousPositionY(int previousPositionY) {
        this.previousPositionY = previousPositionY;
    }
}
