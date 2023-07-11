package org.example;

import asciiPanel.AsciiCharacterData;
import asciiPanel.AsciiPanel;

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
        return panel.getCharacters()[posX][posY];
    }


    //overwrite previous position and write player symbol at new position
    public void drawPlayer(char playerSymbol, int playerPosX, int playerPosY, int previousPlayerPosX,
                     int previousPlayerPosY) {
        panel.write(' ', previousPlayerPosX, previousPlayerPosY);
        panel.write(playerSymbol, playerPosX, playerPosY);

        panel.write('X', 15, 15);

        refresh();
    }
}
