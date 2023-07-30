package org.example.model;

import java.awt.*;

public class Monster extends Entity {
    private final char monsterSymbol;
    private int positionX;
    private int positionY;
    private int previousPositionX;
    private int previousPositionY;
    private final int maxLifePoints;
    private int lifePoints;
    private boolean isAlive;
    private Color monsterColor;

    public Monster(char monsterSymbol, int positionX, int positionY, int lifePoints, Color monsterColor) {
        super(monsterSymbol, positionX, positionY, lifePoints);
        this.monsterSymbol = monsterSymbol;
        this.positionX = positionX;
        this.positionY = positionY;
        this.lifePoints = lifePoints;
        this.maxLifePoints = lifePoints;
        this.monsterColor = monsterColor;
        this.isAlive = true;
    }

    public void die() {
        this.isAlive = false;
        this.monsterColor = Color.WHITE;
        positionX = 0;
        positionY = 0;
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

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getMaxLifePoints() {
        return maxLifePoints;
    }

    public Color getMonsterColor() {
        return monsterColor;
    }
}
