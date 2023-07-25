package org.example.model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Rouge {
    private static final char PLAYER_SYMBOL = '@';
    private static final char MONSTER_SYMBOL = 'x';
    private static final int MONSTER_LIFEPOINTS = 3;
    private static final int PLAYER_LIFEPOINTS = 5;
    private static final int PLAYER_STARTPOS_X = 20;
    private static final int PLAYER_STARTPOS_Y = 20;
    private Map map;
    private Player player;
    private ArrayList<Monster> monsters = new ArrayList<>();

    public Rouge(int mapSizeX, int mapSizeY) {
        this.map = new Map(mapSizeX, mapSizeY);
        this.player = new Player(PLAYER_SYMBOL, PLAYER_STARTPOS_X, PLAYER_STARTPOS_Y, PLAYER_LIFEPOINTS);

        map.initStartChamber(player);
        map.initRandomRectangles();

        //spawn random number of monsters at random map location
        int amountOfMonsters = ThreadLocalRandom.current().nextInt(5, 10);
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
}
