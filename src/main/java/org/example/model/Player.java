package org.example.model;

public class Player {
    private final char playerSymbol;
    private int playerPosX;
    private int playerPosY;
    private int previousPlayerPosX;
    private int previousPlayerPosY;

    public Player(char playerSymbol, int initPlayerPosX, int initPlayerPosY) {
        this.playerSymbol = playerSymbol;
        this.playerPosX = initPlayerPosX;
        this.playerPosY = initPlayerPosY;

        this.previousPlayerPosX = playerPosX;
        this.previousPlayerPosY = playerPosY;
    }

    public char getPlayerSymbol() {
        return this.playerSymbol;
    }

    public int getPlayerPosX() {
        return playerPosX;
    }

    public void setPlayerPosX(int playerPosX) {
        this.playerPosX = playerPosX;
    }

    public int getPlayerPosY() {
        return playerPosY;
    }

    public void setPlayerPosY(int playerPosY) {
        this.playerPosY = playerPosY;
    }

    public int getPreviousPlayerPosX() {
        return previousPlayerPosX;
    }

    public void setPreviousPlayerPosX(int previousPlayerPosX) {
        this.previousPlayerPosX = previousPlayerPosX;
    }

    public int  getPreviousPlayerPosY() {
        return previousPlayerPosY;
    }

    public void setPreviousPlayerPosY(int previousPlayerPosY) {
        this.previousPlayerPosY = previousPlayerPosY;
    }
}
