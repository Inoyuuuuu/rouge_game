package org.example.model;

public class Monster extends Entity{
    private final char monsterSymbol;
    private int positionX;
    private int positionY;
    private int previousPositionX;
    private int previousPositionY;

    public Monster(char monsterSymbol, int positionX, int positionY) {
        super(monsterSymbol, positionX, positionY);
        this.monsterSymbol = monsterSymbol;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public char getMonsterSymbol() {
        return monsterSymbol;
    }

    @Override
    public int getPositionX() {
        return positionX;
    }

    @Override
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    @Override
    public int getPositionY() {
        return positionY;
    }

    @Override
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    @Override
    public int getPreviousPositionX() {
        return previousPositionX;
    }

    @Override
    public void setPreviousPositionX(int previousPositionX) {
        this.previousPositionX = previousPositionX;
    }

    @Override
    public int getPreviousPositionY() {
        return previousPositionY;
    }

    @Override
    public void setPreviousPositionY(int previousPositionY) {
        this.previousPositionY = previousPositionY;
    }
}
