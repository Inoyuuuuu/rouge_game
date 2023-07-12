package org.example;

import org.example.model.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JFrame;

public class InputHandler implements KeyListener {
    private Player player;
    private boolean wasEscapePressed;
    private final BlockingQueue<KeyEvent> gatherKeystrokes = new LinkedBlockingQueue<>();

    public InputHandler(JFrame inputWindow, Player player) {
        inputWindow.addKeyListener(this);

        this.player = player;
        this.wasEscapePressed = false;
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
            player.setPreviousPlayerPosY(player.getPlayerPosY());
            player.setPreviousPlayerPosX(player.getPlayerPosX());
            player.setPlayerPosY(player.getPlayerPosY() - 1);

            this.gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == 'a') {
            player.setPreviousPlayerPosY(player.getPlayerPosY());
            player.setPreviousPlayerPosX(player.getPlayerPosX());
            player.setPlayerPosX(player.getPlayerPosX() - 1);

            gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == 's') {
            player.setPreviousPlayerPosY(player.getPlayerPosY());
            player.setPreviousPlayerPosX(player.getPlayerPosX());
            player.setPlayerPosY(player.getPlayerPosY() + 1);

            this.gatherKeystrokes.add(e);
        }

        if (e.getKeyChar() == 'd') {
            player.setPreviousPlayerPosY(player.getPlayerPosY());
            player.setPreviousPlayerPosX(player.getPlayerPosX());
            player.setPlayerPosX(player.getPlayerPosX() + 1);

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
