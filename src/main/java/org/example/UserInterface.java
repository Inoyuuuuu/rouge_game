package org.example;

import asciiPanel.AsciiCharacterData;
import asciiPanel.AsciiPanel;
import org.example.model.CellType;
import org.example.model.Map;
import org.example.model.Monster;
import org.example.model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UserInterface extends JFrame {

    private final int width;
    private final int height;
    private final int statsBarHeight;
    private final int widthInPixels;
    private final int heightInPixels;
    private static final int STATS_DISTANCE_LEFT = 25;
    private final AsciiPanel panel;


    UserInterface(int width, int height, int statsBarHeigth) {
        super("RogueTeaching");

        this.width = width;
        this.height = height;
        this.statsBarHeight = statsBarHeigth;
        this.panel = new AsciiPanel(this.width, this.height);

        this.widthInPixels = panel.getCharWidth() * this.width;
        this.heightInPixels = panel.getCharHeight() * this.height;

        double formatWidth = (double) 100 / this.width;
        double formatHeight = (double) 100 / this.height;

        super.add(this.panel);
        super.setSize((int) (this.widthInPixels + 15 * formatWidth), (int) (this.heightInPixels + 15 * formatHeight));
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.repaint();
    }

    public void refresh() {
        super.repaint();
    }

    public AsciiCharacterData getCharCharOfPosition(int posX, int posY) {
        if (isOutOfBounds(posX, posY)) {
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
        panel.clear(' ', player.getPreviousPositionX(), player.getPreviousPositionY(), 1, 1);
        panel.write(player.getPlayerSymbol(), player.getPositionX(), player.getPositionY(), Color.YELLOW);

        refresh();
    }

    public void drawMonster(ArrayList<Monster> monsters) {
        for (Monster monster : monsters) {
            panel.clear(' ', monster.getPreviousPositionX(), monster.getPreviousPositionY(), 1, 1);
            panel.write(monster.getMonsterSymbol(), monster.getPositionX(), monster.getPositionY(), monster.getMonsterColor());
        }
        refresh();
    }

    public void drawMap(Map map) {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                    panel.write(map.getCells()[x][y].getContent(), x, y);

                if (map.getCells()[x][y].getCelltype() == CellType.DOOR) {
                    panel.write('D', x, y, Color.YELLOW);
                }
                if (map.getCells()[x][y].getCelltype() == CellType.FLOOR) {
                    panel.write('X', x, y, Color.CYAN);
                }
                if (map.getCells()[x][y].getCelltype() == CellType.PATH) {
                    panel.write('*', x, y, Color.MAGENTA);
                }
                if (map.getCells()[x][y].getCelltype() == CellType.WALL) {
                    panel.write('#', x, y, Color.WHITE);
                }
                if (map.getCells()[x][y].getCelltype() == CellType.BACKGROUND) {
                    panel.clear(' ', x, y, 1, 1);
                }
            }
        }
        refresh();
    }

    public void initStatsBar(char borderTopBotSymbol,  char borderSidesSymbol, int playerLifePoints) {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < statsBarHeight; j++) {

                panel.write(' ', i, height - j - 1, Color.LIGHT_GRAY, Color.DARK_GRAY);

                if (i == 0 || i == width - 1) {
                    panel.write(borderSidesSymbol, i, height - j - 1, Color.LIGHT_GRAY, Color.DARK_GRAY);
                }
                if (j == 0 || j == statsBarHeight - 1) {
                    panel.write(borderTopBotSymbol, i, height - j - 1, Color.LIGHT_GRAY, Color.DARK_GRAY);
                }
            }
        }

        drawRectangle('*', width - (width / 4) * 3 + STATS_DISTANCE_LEFT - 5, height - statsBarHeight + 1,
                7, 5, false, Color.LIGHT_GRAY, Color.DARK_GRAY);
        drawRectangle('*', width - (width / 4) * 3 + STATS_DISTANCE_LEFT + 3, height - statsBarHeight + 1,
                7, 5, false, Color.LIGHT_GRAY, Color.DARK_GRAY);
        panel.write('P', width - (width / 4) * 3 + STATS_DISTANCE_LEFT - 2, height - statsBarHeight + 1, Color.LIGHT_GRAY, Color.DARK_GRAY);
        panel.write('M', width - (width / 4) * 3 + STATS_DISTANCE_LEFT + 6, height - statsBarHeight + 1, Color.LIGHT_GRAY, Color.DARK_GRAY);

        panel.write("PLAYER HEALTH: ", 1, height - statsBarHeight + 1, Color.WHITE,  Color.DARK_GRAY);
        for (int i = 0; i < playerLifePoints; i++) {
            panel.write('O', 16 + i, height - statsBarHeight + 1, Color.RED, Color.DARK_GRAY);
        }

        panel.write("Waiting for combat...", width - (width / 4) * 3 + STATS_DISTANCE_LEFT + 20, height - statsBarHeight + 1, Color.WHITE, Color.DARK_GRAY);
    }

    public void initMonsterHealth(Monster monster) {
        panel.write("Monster:", width - (width / 4) * 3 + STATS_DISTANCE_LEFT + 20, height - statsBarHeight + 1, Color.WHITE, Color.DARK_GRAY);
        for (int i = 0; i < monster.getMaxLifePoints(); i++) {
            panel.write('O', width - (width / 4) * 3 + STATS_DISTANCE_LEFT + 20 + i, height - statsBarHeight + 2, Color.RED, Color.DARK_GRAY);
        }
        refresh();
    }

    public void clearMonsterHealth(Monster monster) {
        panel.write("Waiting for combat...", width - (width / 4) * 3 + STATS_DISTANCE_LEFT + 20, height - statsBarHeight + 1, Color.WHITE, Color.DARK_GRAY);
        for (int i = 0; i < monster.getMaxLifePoints(); i++) {
            panel.write(' ', width - (width / 4) * 3 + STATS_DISTANCE_LEFT + 20 + i, height - statsBarHeight + 2, Color.RED, Color.DARK_GRAY);
        }
        refresh();
    }

    public void updateMonsterHealth(Monster monster) {
        for (int i = monster.getLifePoints(); i < monster.getMaxLifePoints(); i++) {
            panel.write('X', width - (width / 4) * 3 + STATS_DISTANCE_LEFT + 20 + i, height - statsBarHeight + 2, Color.BLACK, Color.DARK_GRAY);
        }
        refresh();
    }

    public void updatePlayerHealth(Player player) {
        for (int i = player.getLifePoints(); i < player.getMaxLifePoints(); i++) {
            panel.write('X', 16 + i, height - statsBarHeight + 1, Color.BLACK, Color.DARK_GRAY);
        }
        refresh();
    }

    public void updateRolledNumbers(int playerStrengthNumber, int monsterStrengthNumber) {
        panel.write(String.valueOf(playerStrengthNumber), width - (width / 4) * 3 + STATS_DISTANCE_LEFT - 2, height - statsBarHeight / 2 - 1, Color.RED, Color.DARK_GRAY);
        panel.write(String.valueOf(monsterStrengthNumber), width - (width / 4) * 3 + STATS_DISTANCE_LEFT + 6, height - statsBarHeight / 2 - 1, Color.RED, Color.DARK_GRAY);
        refresh();
    }

    public void clearRolledNumbers() {
        panel.write(' ', width - (width / 4) * 3 + STATS_DISTANCE_LEFT - 2, height - statsBarHeight / 2 - 1, Color.WHITE, Color.DARK_GRAY);
        panel.write(' ', width - (width / 4) * 3 + STATS_DISTANCE_LEFT + 6, height - statsBarHeight / 2 - 1, Color.WHITE, Color.DARK_GRAY);
        refresh();
    }

    public void drawRollButton(int sizeX, int sizeY, char buttonChar, String text, Color foreColor, Color backColor) {
        int posX = STATS_DISTANCE_LEFT;
        int posY = height - 5;

        drawRectangle(buttonChar, posX, posY, sizeX, sizeY, buttonChar, foreColor, backColor);
        panel.write(text, posX + 1, posY + sizeY / 2, foreColor, backColor);
    }

    //draws a border around the panel
    public void drawBorder(char wallSymbol, int statsBarHeight) {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || i == width - 1|| j == 0 || j == height - statsBarHeight - 1) {
                    panel.write(wallSymbol, i, j);
                }
            }
        }
        refresh();
    }

    //---------------------------------------------------------------------------------------------

    // draw a rectangle at a specific position
    public void drawRectangle(char character, int posX, int posY, int width, int height, boolean isFilled, Color color, Color backgroundColor) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if (isFilled) {
                    panel.write(character, posX + i, posY + j, color, backgroundColor);
                } else {
                    if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                        panel.write(character, posX + i, posY + j, color, backgroundColor);
                    }
                }
            }
        }
    }

    // draw a rectangle at a specific position and fill it with a custom char
    public void drawRectangle(char character, int posX, int posY, int width, int height, char fillChar, Color foreColor, Color backColor) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                    panel.write(character, posX + i, posY + j, foreColor, backColor);
                } else {
                    panel.write(fillChar, posX + i, posY + j, foreColor, backColor);
                }
            }
        }
    }

    public void debug_drawPoint(int posX, int posY) {
        panel.write('.', posX, posY, Color.GREEN);
        refresh();
    }
}
