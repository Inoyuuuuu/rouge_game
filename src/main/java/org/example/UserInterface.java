package org.example;

import asciiPanel.AsciiCharacterData;
import asciiPanel.AsciiPanel;

import javax.swing.*;
import java.awt.*;

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

    public AsciiCharacterData getCharCharOfPosition(int posX, int posY) {
        if (isPositionInPanel(posX, posY)) {
            return panel.getCharacters()[posX][posY];
        }
        return null;
    }

    public void refresh() {
        super.repaint();
    }

    //overwrite previous position and write player symbol at new position
    public void drawPlayer(char playerSymbol, int playerPosX, int playerPosY, int previousPlayerPosX,
                     int previousPlayerPosY) {
        panel.write(' ', previousPlayerPosX, previousPlayerPosY);
        panel.write(playerSymbol, playerPosX, playerPosY);

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

    public boolean isPositionInPanel(int posX, int posY) {
        return posX < this.width - 2 && posX > 0 &&
                posY < this.height - 2 && posY > 0;
    }
}
