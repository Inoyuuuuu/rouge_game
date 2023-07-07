package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.*;

public class InputHandler implements KeyListener {
    private int playerPositionX;
    private int playerPositionY;
    private int previousPlayerPositionX;
    private int previousPlayerPositionY;
    private boolean wasEscapePressed;
    private final BlockingQueue<KeyEvent> gatherKeystrokes = new LinkedBlockingQueue<>();

    public InputHandler(JFrame inputWindow, int playerPositionX, int playerPositionY) {
        inputWindow.addKeyListener(this);
        this.playerPositionX = playerPositionX;
        this.playerPositionY = playerPositionY;
        this.previousPlayerPositionX = playerPositionX;
        this.previousPlayerPositionY = playerPositionY;
        this.wasEscapePressed = false;
    }

    public int getPlayerPositionX() {
        return playerPositionX;
    }

    public void setPlayerPositionX(int playerPositionX) {
        this.playerPositionX = playerPositionX;
    }

    public int getPlayerPositionY() {
        return playerPositionY;
    }

    public void setPlayerPositionY(int playerPositionY) {
        this.playerPositionY = playerPositionY;
    }

    public int getPreviousPlayerPositionX() {
        return previousPlayerPositionX;
    }

    public int getPreviousPlayerPositionY() {
        return previousPlayerPositionY;
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

        //prints current keystroke
        //System.out.println(e.getKeyChar());

        if (e.getKeyChar() == 'w') {
            this.previousPlayerPositionY = playerPositionY;
            this.previousPlayerPositionX = playerPositionX;
            this.playerPositionY--;

            this.gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == 'a') {
            this.previousPlayerPositionY = playerPositionY;
            this.previousPlayerPositionX = playerPositionX;
            this.playerPositionX--;

            gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == 's') {
            this.previousPlayerPositionY = playerPositionY;
            this.previousPlayerPositionX = playerPositionX;
            this.playerPositionY++;

            this.gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == 'd') {
            this.previousPlayerPositionY = playerPositionY;
            this.previousPlayerPositionX = playerPositionX;
            this.playerPositionX++;

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
