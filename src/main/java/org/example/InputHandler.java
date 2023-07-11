package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JFrame;

public class InputHandler implements KeyListener {
    private int calculatedPlayerPosX;
    private int calculatedPlayerPosY;
    private int currentPlayerPosX;
    private int currentPlayerPosY;
    private boolean wasEscapePressed;
    private final BlockingQueue<KeyEvent> gatherKeystrokes = new LinkedBlockingQueue<>();

    public InputHandler(JFrame inputWindow, int initPlayerPositionX, int initPlayerPositionY) {
        inputWindow.addKeyListener(this);

        this.calculatedPlayerPosX = initPlayerPositionX;
        this.calculatedPlayerPosY = initPlayerPositionY;
        this.currentPlayerPosX = calculatedPlayerPosX;
        this.currentPlayerPosY = calculatedPlayerPosY;

        this.wasEscapePressed = false;
    }

    public int getCalculatedPlayerPosX() {
        return calculatedPlayerPosX;
    }

    public void setCalculatedPlayerPosX(int calculatedPlayerPosX) {
        this.calculatedPlayerPosX = calculatedPlayerPosX;
    }

    public int getCalculatedPlayerPosY() {
        return calculatedPlayerPosY;
    }

    public void setCalculatedPlayerPosY(int calculatedPlayerPosY) {
        this.calculatedPlayerPosY = calculatedPlayerPosY;
    }

    public int getCurrentPlayerPosX() {
        return currentPlayerPosX;
    }

    public int getCurrentPlayerPosY() {
        return currentPlayerPosY;
    }

    public BlockingQueue<KeyEvent> getGatherKeystrokes() {
        return gatherKeystrokes;
    }

    public boolean wasEscapePressed() {
        return wasEscapePressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        //System.out.println(e.getKeyChar());  <- prints current keystroke

        if (e.getKeyChar() == 'w') {
            this.currentPlayerPosY = calculatedPlayerPosY;
            this.currentPlayerPosX = calculatedPlayerPosX;
            this.calculatedPlayerPosY--;

            this.gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == 'a') {
            this.currentPlayerPosY = calculatedPlayerPosY;
            this.currentPlayerPosX = calculatedPlayerPosX;
            this.calculatedPlayerPosX--;

            gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == 's') {
            this.currentPlayerPosY = calculatedPlayerPosY;
            this.currentPlayerPosX = calculatedPlayerPosX;
            this.calculatedPlayerPosY++;

            this.gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == 'd') {
            this.currentPlayerPosY = calculatedPlayerPosY;
            this.currentPlayerPosX = calculatedPlayerPosX;
            this.calculatedPlayerPosX++;

            this.gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {

            this.wasEscapePressed = true;

            System.out.println("closing game...");
            gatherKeystrokes.add(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
