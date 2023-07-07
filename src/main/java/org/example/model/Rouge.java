package org.example.model;

import model.Map;
public class Rouge {

    private final Map map;
    private final Player player;

    public Rouge(Character playerSymbol, int playerPositionX, int playerPositionY) {
        this.map = new Map();
        this.player = new Player(playerSymbol, playerPositionX, playerPositionY);
    }

    public Map getMap() {
        return map;
    }
    public Player getPlayer() {
        return player;
    }
}
