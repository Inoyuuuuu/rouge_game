package org.example.model;

public abstract class Entity {
    private final char entitySymbol;
    private int positionX;
    private int positionY;
    private int previousPositionX;
    private int previousPositionY;

    public Entity(char entitySymbol, int positionX, int positionY) {
        this.entitySymbol = entitySymbol;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public char getEntitySymbol() {
        return entitySymbol;
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
