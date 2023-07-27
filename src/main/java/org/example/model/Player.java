package org.example.model;

public class Player extends Entity{
    private final char playerSymbol;
    private int positionX;
    private int positionY;
    private int previousPositionX;
    private int previousPositionY;
    private final int maxLifePoints;
    private int lifePoints;
    boolean isInBattle;

    public Player(char playerSymbol, int initPlayerPosX, int initPlayerPosY, int lifePoints) {
        super(playerSymbol, initPlayerPosX, initPlayerPosY, lifePoints);

        this.playerSymbol = playerSymbol;
        this.positionX = initPlayerPosX;
        this.positionY = initPlayerPosY;
        this.maxLifePoints = lifePoints;
        this.lifePoints = lifePoints;

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

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public int getMaxLifePoints() {
        return maxLifePoints;
    }

    public boolean isInBattle() {
        return isInBattle;
    }

    public void setInBattle(boolean inBattle) {
        isInBattle = inBattle;
    }
}
