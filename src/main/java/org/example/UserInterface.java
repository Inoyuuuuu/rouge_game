package org.example;

import asciiPanel.AsciiCharacterData;
import asciiPanel.AsciiPanel;
import org.example.model.Player;

import javax.swing.*;

public class UserInterface extends JFrame {

    private final int width;
    private final int height;
    private final int widthInPixels;
    private final int heightInPixels;
    private final AsciiPanel panel;


    UserInterface(int width, int height) {
        super("RogueTeaching");

        this.width = width;
        this.height = height;
        this.panel = new AsciiPanel(this.width, this.height);

        this.widthInPixels = panel.getCharWidth() * this.width;
        this.heightInPixels = panel.getCharHeight() * this.height;

        super.add(this.panel);
        super.setSize(this.widthInPixels, this.heightInPixels);
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.repaint();
    }

    public void refresh() {
        super.repaint();
    }

    public AsciiCharacterData getCharCharOfPosition(int posX, int posY) {
        if (isOutOfBounds(posX, posY) == true) {
            return null;
        }
        return panel.getCharacters()[posX][posY];
    }

    public boolean isOutOfBounds(int posX, int posY) {
        if (posX + 1 >= width || posX <= 0) {
            return true;
        }
        if (posY + 1 >= height || posY <= 0) {
            return true;
        }

        return false;
    }


    //overwrite previous position and write player symbol at new position
    public void drawPlayer(Player player) {
        panel.write(' ', player.getPreviousPlayerPosX(), player.getPreviousPlayerPosY());
        panel.write(player.getPlayerSymbol(), player.getPlayerPosX(), player.getPlayerPosY());

        refresh();
    }

    //draws a border around the panel
    public void drawBorder(char wallSymbol) {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 1; k < 4; k++) {

                    if (i == 0 || i == width - k || j == 0 || j == height - k) {
                        panel.write(wallSymbol, i, j);
                    }
                }
            }
        }

        refresh();
    }

    // draw a rectangle at a specific position
    public void drawRectangle(char character, int posX, int posY, int width, int height, boolean isFilled) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if (isFilled) {
                    panel.write(character, posX + i, posY + j);
                } else {
                    if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                        panel.write(character, posX + i, posY + j);
                    }
                }
            }
        }
    }

    // draw a rectangle at a specific position and fill it with a custom char
    public void drawRectangle(char character, int posX, int posY, int width, int height, char fillChar) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                    panel.write(character, posX + i, posY + j);
                } else {
                    panel.write(fillChar, posX + i, posY + j);
                }
            }
        }
    }
}
