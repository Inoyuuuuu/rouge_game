package org.example;

import org.example.model.CellType;
import org.example.model.Map;
import org.example.model.Player;
import org.example.model.Rouge;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Main class of the program
 * @author ukuny
 *
 */
public class Main {

    /**
     * the entry point to the rouge program
     *
     * @param args not used
     * @throws InterruptedException throw because of BlockingQueue.take()
     */
    public static void main(String[] args) throws InterruptedException {

        int gameWindowWidth = 120;
        int gameWindowHeight = 40;

        int statsBarHeight = 7;

        Rouge rouge = new Rouge(gameWindowWidth, gameWindowHeight);
        Player player = rouge.getPlayer();
        Map map = rouge.getMap();

        UserInterface ui = new UserInterface(gameWindowWidth, gameWindowHeight + statsBarHeight);
        InputHandler inputHandler = new InputHandler(ui, rouge.getPlayer());

        ui.drawMap(map);
        ui.drawBorder('X', statsBarHeight);
        ui.initStatsBar(statsBarHeight, '=', '|', rouge.getPlayer().getLifePoints());


        //main loop
        while (!inputHandler.wasEscapePressed()) {

            player.setPreviousPositionX(player.getPositionX());
            player.setPreviousPositionY(player.getPositionY());

            int nextPosX = inputHandler.getUpdatedPlayerPositionX();
            int nextPosY = inputHandler.getUpdatedPlayerPositionY();

            //wall detection
            if (!ui.isOutOfBounds(nextPosX, nextPosY)
                    && map.getCells()[nextPosX][nextPosY].getCelltype() != CellType.WALL) {

                player.setPositionX(nextPosX);
                player.setPositionY(nextPosY);

            } else {

                //reset position
                inputHandler.setUpdatedPlayerPositionX(player.getPreviousPositionX());
                inputHandler.setUpdatedPlayerPositionY(player.getPreviousPositionY());
            }

            //print player + surroundings in console
            print3x3field(player.getPositionX(), player.getPositionY(), ui);

            //draw player
            ui.drawPlayer(player);

            //move monsters
            for (int i = 0; i < rouge.getMonsters().size(); i++) {
                boolean isMoveValid = false;

                while (!isMoveValid) {
                    int nextMonsterPosX = rouge.getMonsters().get(i).getPositionX()
                            + ThreadLocalRandom.current().nextInt(-1, 2);
                    int nextMonsterPosY = rouge.getMonsters().get(i).getPositionY()
                            + ThreadLocalRandom.current().nextInt(-1, 2);

                    if (map.getCells()[nextMonsterPosX][nextMonsterPosY].getCelltype() == CellType.CHAMBER) {

                        rouge.getMonsters().get(i).setPreviousPositionX(rouge.getMonsters().get(i).getPositionX());
                        rouge.getMonsters().get(i).setPreviousPositionY(rouge.getMonsters().get(i).getPositionY());

                        rouge.getMonsters().get(i).setPositionX(nextMonsterPosX);
                        rouge.getMonsters().get(i).setPositionY(nextMonsterPosY);

                        isMoveValid = true;
                    }
                }

            }
            ui.drawMonster(rouge.getMonsters());
            inputHandler.getGatherKeystrokes().take();
        }

        //after while loop, close window
        ui.setVisible(false);
        ui.dispose();
    }

    /** dev tool
     * this method gets a position and prints all the chars next to it
     *
     * @param posX position X
     * @param posY position Y
     * @param ui the UserInterface with the ascii-panel the characters are on
     */
    private static void print3x3field(int posX, int posY, UserInterface ui) {

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {

                if (ui.isOutOfBounds(posX + i - 1, posY + j - 1)) {
                    System.out.println("meh");
                } else {
                    char currentChar = ui.getCharCharOfPosition(posX + i - 1, posY + j - 1).character;

                    if (i == 1 && j == 1) {
                        System.out.print("@ ");
                    } else {
                        if (Character.isWhitespace(currentChar) || currentChar == '@') {
                            System.out.print(". ");
                        } else {
                            System.out.print(ui.getCharCharOfPosition(posX + i - 1, posY + j - 1).character
                                    + " ");
                        }
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}