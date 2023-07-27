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
                if (rouge.getMonsters().get(i).isAlive()) {
                    boolean isMoveValid = false;

                    while (!isMoveValid) {
                        int nextMonsterPosX = rouge.getMonsters().get(i).getPositionX()
                                + ThreadLocalRandom.current().nextInt(-1, 2)
                                * ThreadLocalRandom.current().nextInt(0, 2);
                        int nextMonsterPosY = rouge.getMonsters().get(i).getPositionY()
                                + ThreadLocalRandom.current().nextInt(-1, 2)
                                * ThreadLocalRandom.current().nextInt(0, 2);

                        rouge.getMonsters().get(i).setPreviousPositionX(rouge.getMonsters().get(i).getPositionX());
                        rouge.getMonsters().get(i).setPreviousPositionY(rouge.getMonsters().get(i).getPositionY());

                        if (map.getCells()[nextMonsterPosX][nextMonsterPosY].getCelltype() == CellType.CHAMBER) {

                            rouge.getMonsters().get(i).setPositionX(nextMonsterPosX);
                            rouge.getMonsters().get(i).setPositionY(nextMonsterPosY);

                            isMoveValid = true;
                        }
                    }
                    printCurrentPos(rouge.getPlayer(), rouge.getMonsters(), 0);
                }
            }

            //draw entities
            ui.drawMonster(rouge.getMonsters());
            ui.drawPlayer(rouge.getPlayer());

            //print player + surroundings in console
            print3x3field(player.getPositionX(), player.getPositionY(), ui);

            printCurrentPos(rouge.getPlayer(), rouge.getMonsters(), 1);

            checkIfBattle(rouge.getPlayer(), rouge.getMonsters(), ui, inputHandler);

            inputHandler.getGatherKeystrokes().take();
        }

        //after while loop, close window
        closeGame(ui);
    }

    public static void closeGame(UserInterface ui) {
        System.out.println("closing game...");
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

    private static void printCurrentPos(Player player, ArrayList<Monster> monsters, int version) {
        System.out.println(version);
        for (int i = 0; i < monsters.size(); i++) {
            System.out.println(i + "oM:" + monsters.get(i).getPositionX() + ", " + monsters.get(i).getPositionY());
        }
        System.out.println("oMP:" + player.getPositionX() + ", " + player.getPositionY());
    }

    private static void checkIfBattle(Player player, ArrayList<Monster> monsters, UserInterface ui, InputHandler inputHandler) throws InterruptedException {
        for (Monster monster : monsters) {
            if (player.getPositionX() == monster.getPositionX()
                    && player.getPositionY() == monster.getPositionY()) {
                battle(player, monster, ui, inputHandler);
            }
        }
    }

    public static void battle(Player player, Monster monster, UserInterface ui, InputHandler inputHandler) throws InterruptedException {
        boolean isBattleOver = false;
        player.setInBattle(true);
        System.out.println("battle begins");

        ui.initMonsterHealth(monster);

        while (!isBattleOver) {
            int playerStrength;
            int monsterStrength;

            inputHandler.getGatherKeystrokes().take();

            if (inputHandler.wasEnterPressed()) {
                playerStrength = ThreadLocalRandom.current().nextInt(0, 10);
                monsterStrength = ThreadLocalRandom.current().nextInt(0, 10);

                ui.updateStatsBar(playerStrength, monsterStrength);

                if (playerStrength > monsterStrength) {
                    if (monster.getLifePoints() - 1 > 0) {
                        monster.setLifePoints(monster.getLifePoints() - 1);
                        System.out.println("You won the round!");

                    } else {
                        System.out.println("monster ded");
                        monster.setLifePoints(0);
                        ui.updateMonsterHealth(monster);
                        monster.die();
                        isBattleOver = true;
                    }
                }

                if (monsterStrength > playerStrength) {
                    if (player.getLifePoints() - 1 > 0) {
                        player.setLifePoints(player.getLifePoints() - 1);
                        ui.updatePlayerHealth(player);
                        System.out.println("The monster won the round!");

                    } else {
                        System.out.println("player ded");
                        player.setLifePoints(0);
                        ui.updatePlayerHealth(player);
                        isBattleOver = true;
                        closeGame(ui);
                    }
                }

                if (monsterStrength == playerStrength) {
                    System.out.println("tie. Press Enter to roll again!");
                }

                ui.updateMonsterHealth(monster);
                inputHandler.setEnterPressed(false);
            }
        }
        player.setInBattle(false);
    }
}