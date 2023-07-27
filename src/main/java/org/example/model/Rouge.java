package org.example.model;

import org.example.InputHandler;
import org.example.UserInterface;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Rouge {
    private static final char PLAYER_SYMBOL = '@';
    private static final char MONSTER_SYMBOL = 'X';
    private static final int MONSTER_LIFEPOINTS = 3;
    private static final int PLAYER_LIFEPOINTS = 5;
    private static final int PLAYER_STARTPOS_X = 20;
    private static final int PLAYER_STARTPOS_Y = 20;
    private final int amountOfMonsters = ThreadLocalRandom.current().nextInt(8, 10);
    private Map map;
    private Player player;
    private ArrayList<Monster> monsters = new ArrayList<>();

    public Rouge(int mapSizeX, int mapSizeY) {
        this.map = new Map(mapSizeX, mapSizeY);
        this.player = new Player(PLAYER_SYMBOL, PLAYER_STARTPOS_X, PLAYER_STARTPOS_Y, PLAYER_LIFEPOINTS);

        map.initStartChamber(player);
        map.initRandomRectangles();

        //spawn random number of monsters at random map location
        for (int i = 0; i < amountOfMonsters; i++) {
            int[] randomPos = map.getAvailableMonsterSpawnPoints()
                    .get(ThreadLocalRandom.current().nextInt(0, map.getAvailableMonsterSpawnPoints().size()));
            monsters.add(new Monster(MONSTER_SYMBOL, randomPos[0], randomPos[1], MONSTER_LIFEPOINTS));
        }
    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void battle(Player player, Monster monster, UserInterface ui, InputHandler inputHandler) throws InterruptedException {
        boolean isBattleOver = false;
        System.out.println("battle begins");

        while (!isBattleOver) {
            int playerStrength;
            int monsterStrength;

            if (inputHandler.wasEnterPressed()) {
                playerStrength = ThreadLocalRandom.current().nextInt(0, 2);
                monsterStrength = ThreadLocalRandom.current().nextInt(0, 1);

                ui.updateStatsBar(playerStrength, monsterStrength);

                if (playerStrength > monsterStrength) {
                    if (monster.getLifePoints() - 1 > 0) {
                        monster.setLifePoints(monster.getLifePoints() - 1);
                        System.out.println("You won the round!");

                    } else {
                        System.out.println("monster ded");
                        //TODO:make monster ded
                        isBattleOver = true;
                    }
                }

                if (monsterStrength > playerStrength) {
                    if (player.getLifePoints() - 1 > 0) {
                        player.setLifePoints(player.getLifePoints() - 1);
                        System.out.println("The monster won the round!");

                    } else {
                        System.out.println("player ded");
                        //TODO:make player ded
                        isBattleOver = true;
                    }
                }

                if (monsterStrength == playerStrength) {
                    System.out.println("tie. Press Enter to roll again!");
                }

                inputHandler.setEnterPressed(false);
            }

            inputHandler.getGatherKeystrokes().take();
        }
    }
}
