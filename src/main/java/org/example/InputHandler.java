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
    private final BlockingQueue<KeyEvent> gatherKeystrokes = new LinkedBlockingQueue<>();

    public InputHandler(JFrame inputWindow, int playerPositionX, int playerPositionY) {
        inputWindow.addKeyListener(this);
        this.playerPositionX = playerPositionX;
        this.playerPositionY = playerPositionY;
        this.previousPlayerPositionX = playerPositionX;
        this.previousPlayerPositionY = playerPositionY;
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());

        if (e.getKeyChar() == 'w') {
            previousPlayerPositionY = playerPositionY;
            previousPlayerPositionX = playerPositionX;
            playerPositionY--;

            System.out.println(playerPositionX + "," + playerPositionY);
            gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == 'a') {
            previousPlayerPositionY = playerPositionY;
            previousPlayerPositionX = playerPositionX;
            playerPositionX--;

            System.out.println(playerPositionX + "," + playerPositionY);
            gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == 's') {
            previousPlayerPositionY = playerPositionY;
            previousPlayerPositionX = playerPositionX;
            playerPositionY++;

            System.out.println(playerPositionX + "," + playerPositionY);
            gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == 'd') {
            previousPlayerPositionY = playerPositionY;
            previousPlayerPositionX = playerPositionX;
            playerPositionX++;

            System.out.println(playerPositionX + "," + playerPositionY);
            gatherKeystrokes.add(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
