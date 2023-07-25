package org.example;

import org.example.model.CellType;
import org.example.model.Map;
import org.example.model.Player;
import org.example.model.Rougue;

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

        Rougue rougue = new Rougue(gameWindowWidth, gameWindowHeight);
        Player player = rougue.getPlayer();
        Map map = rougue.getMap();

        UserInterface ui = new UserInterface(gameWindowWidth, gameWindowHeight);
        InputHandler inputHandler = new InputHandler(ui, rougue.getPlayer());

        ui.drawMap(map);

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
            ui.drawMonster(rougue.getMonsters());

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