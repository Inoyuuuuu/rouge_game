package org.example;

import org.example.model.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

        UserInterface ui = new UserInterface(gameWindowWidth, gameWindowHeight + statsBarHeight, statsBarHeight);
        InputHandler inputHandler = new InputHandler(ui, rouge.getPlayer());

        ui.drawMap(map);
        ui.drawBorder('X', statsBarHeight);
        ui.initStatsBar('=', '|', rouge.getPlayer().getLifePoints());

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

            //move monsters
            for (int i = 0; i < rouge.getMonsters().size(); i++) {
                boolean isMoveValid = false;

                while (!isMoveValid) {
                    int nextMonsterPosX = rouge.getMonsters().get(i).getPositionX()
                            + ThreadLocalRandom.current().nextInt(-1, 2)
                            * ThreadLocalRandom.current().nextInt(0, 2);
                    int nextMonsterPosY = rouge.getMonsters().get(i).getPositionY()
                            + ThreadLocalRandom.current().nextInt(-1, 2)
                            * ThreadLocalRandom.current().nextInt(0, 2);

                    if (map.getCells()[nextMonsterPosX][nextMonsterPosY].getCelltype() == CellType.CHAMBER) {

                        rouge.getMonsters().get(i).setPreviousPositionX(rouge.getMonsters().get(i).getPositionX());
                        rouge.getMonsters().get(i).setPreviousPositionY(rouge.getMonsters().get(i).getPositionY());

                        rouge.getMonsters().get(i).setPositionX(nextMonsterPosX);
                        rouge.getMonsters().get(i).setPositionY(nextMonsterPosY);
                        isMoveValid = true;
                    }
                }
            }

            //print player + surroundings in console
            print3x3field(player.getPositionX(), player.getPositionY(), ui);

            //draw entities
            ui.drawMonster(rouge.getMonsters());
            ui.drawPlayer(rouge.getPlayer());
            
            checkIfBattle(rouge.getPlayer(), rouge.getMonsters(), ui, inputHandler, rouge);

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

    private static void checkIfBattle(Player player, ArrayList<Monster> monsters, UserInterface ui, InputHandler inputHandler, Rouge rouge) throws InterruptedException {
        for (Monster monster : monsters) {
            if (player.getPositionX() == monster.getPositionX()
                    && player.getPositionY() == monster.getPositionY()) {
                //battle(player, monster, ui, inputHandler);
                rouge.battle(player, monster, ui, inputHandler);
            }
        }
        System.out.println("cib: " + player.getPositionX() + ", " + player.getPositionY());

    }

    private static void battle(Player player, Monster monster, UserInterface ui, InputHandler inputHandler) throws InterruptedException {
        int playerStrength;
        int monsterStrength;

        System.out.println("battle begins");

        playerStrength = ThreadLocalRandom.current().nextInt(0, 2);
        monsterStrength = ThreadLocalRandom.current().nextInt(0, 1);

        ui.updateStatsBar(playerStrength, monsterStrength);

        if (playerStrength > monsterStrength) {
            if (monster.getLifePoints() - 1 > 0) {
                monster.setLifePoints(monster.getLifePoints() - 1);
            } else {
                System.out.println("monster ded");
                //TODO:make monster ded
            }
        }

        if (monsterStrength > playerStrength) {
            if (player.getLifePoints() - 1 > 0) {
                player.setLifePoints(player.getLifePoints() - 1);
            } else {
                System.out.println("player ded");
                //TODO:make player ded
            }
        }

        if (monsterStrength == playerStrength) {
            System.out.println("tie. Press Enter to roll again!");
        }
    }
}