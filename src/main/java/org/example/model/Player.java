package org.example.model;

public class Player extends Entity {
    private Character playerSymbol;

    private int posX;
    private int posY;

    public Player(Character playerSymbol, int posX, int posY) {
        super(playerSymbol);

        this.playerSymbol = playerSymbol;
        this.posX = posX;
        this.posY = posY;
    }

    public Character getPlayerSymbol() {
        return playerSymbol;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
