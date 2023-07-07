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
        this.panel.setDefaultBackgroundColor(Color.BLUE);

        this.widthInPixels = panel.getCharWidth() * this.width;
        System.out.println(panel.getCharWidth());
        this.heightInPixels = panel.getCharHeight() * this.height;
        System.out.println(panel.getCharHeight());

        super.add(this.panel);
        super.setSize(this.widthInPixels, this.heightInPixels);
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.repaint();
    }

    public AsciiCharacterData getCharCharOfPosition(int posX, int posY) {
        return panel.getCharacters()[posX][posY];
    }

    public void refresh() {
        super.repaint();
    }

    public void draw(char playerSymbol, int playerPosX, int playerPosY, int previousPlayerPosX,
                     int previousPlayerPosY) {

        panel.write(playerSymbol, playerPosX, playerPosY);
        panel.write(' ', previousPlayerPosX, previousPlayerPosY);
        panel.write('X', 20, 20);
        panel.write('z', 10, 11);

/*        panel.withEachTile(  (x, y, asciiCharacterData) -> {
            asciiCharacterData.character = '.';
            asciiCharacterData.foregroundColor =  Color.CYAN;
            //asciiCharacterData.backgroundColor = Color.BLUE;
        });*/

        refresh();
    }


}
