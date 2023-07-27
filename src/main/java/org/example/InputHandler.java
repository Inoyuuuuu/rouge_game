package org.example;

import org.example.model.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JFrame;

public class InputHandler implements KeyListener {
    private int updatedPlayerPositionX;
    private int updatedPlayerPositionY;
    private boolean wasEnterPressed;
    private boolean wasEscapePressed;
    private final Player player;
    private final BlockingQueue<KeyEvent> gatherKeystrokes = new LinkedBlockingQueue<>();

    public InputHandler(JFrame inputWindow, Player player) {
        inputWindow.addKeyListener(this);

        this.player = player;
        updatedPlayerPositionX = player.getPositionX();
        updatedPlayerPositionY = player.getPositionY();

        this.wasEscapePressed = false;
        this.wasEnterPressed = false;
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

        if (e.getKeyChar() == 'w') {
            if (!player.isInBattle()) {
                updatedPlayerPositionY--;

                this.gatherKeystrokes.add(e);
            }
        }

        if (e.getKeyChar() == 'a') {
            if (!player.isInBattle()) {
                updatedPlayerPositionX--;

                gatherKeystrokes.add(e);
            }
        }

        if (e.getKeyChar() == 's') {
            if (!player.isInBattle()) {
                updatedPlayerPositionY++;

                this.gatherKeystrokes.add(e);
            }
        }

        if (e.getKeyChar() == 'd') {
            if (!player.isInBattle()) {
                updatedPlayerPositionX++;

                this.gatherKeystrokes.add(e);
            }
        }

        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {

            this.wasEscapePressed = true;

            System.out.println("closing game...");
            gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == KeyEvent.VK_ENTER) {

            this.wasEnterPressed = true;
            gatherKeystrokes.add(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public int getUpdatedPlayerPositionX() {
        return updatedPlayerPositionX;
    }

    public void setUpdatedPlayerPositionX(int updatedPlayerPositionX) {
        this.updatedPlayerPositionX = updatedPlayerPositionX;
    }

    public int getUpdatedPlayerPositionY() {
        return updatedPlayerPositionY;
    }

    public void setUpdatedPlayerPositionY(int updatedPlayerPositionY) {
        this.updatedPlayerPositionY = updatedPlayerPositionY;
    }

    public boolean wasEnterPressed() {
        return wasEnterPressed;
    }

    public void setEnterPressed(boolean wasEnterPressed) {
        this.wasEnterPressed = wasEnterPressed;
    }
}
