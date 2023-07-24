package org.example.model;

import java.util.LinkedList;

public class Path {
    private int startX;
    private int startY;
    private final LinkedList<int[]> pathCoordinates = new LinkedList<>();

    public Path(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public LinkedList<int[]> getPathCoordinates() {
        return pathCoordinates;
    }
}
