package org.example.model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Rougue {
    private static final char PLAYER_SYMBOL = '@';
    private static final char MONSTER_SYMBOL = 'x';
    private Map map;
    private Player player;
    private ArrayList<Monster> monsters = new ArrayList<>();

    public Rougue(int mapSizeX, int mapSizeY) {
        this.map = new Map(mapSizeX, mapSizeY);
        this.player = new Player(PLAYER_SYMBOL, 20, 20);

        map.initStartChamber(player);
        map.initRandomRectangles();

        //spawn random number of monsters at random map location
        int amountOfMonsters = ThreadLocalRandom.current().nextInt(5, 10);
        for (int i = 0; i < amountOfMonsters; i++) {
            int[] randomPos = map.getAvailableMonsterSpawnPoints()
                    .get(ThreadLocalRandom.current().nextInt(0, map.getAvailableMonsterSpawnPoints().size()));
            monsters.add(new Monster(MONSTER_SYMBOL, randomPos[0], randomPos[1]));
        }
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
}
