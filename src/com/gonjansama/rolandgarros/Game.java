package com.gonjansama.rolandgarros;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private Player player1;
    private Player player2;
    private Map<Player, Integer> score = new HashMap<Player, Integer>();

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void start() {
        score.put(player1, 0);
        score.put(player2, 0);
    }

    public Map<Player, Integer> getScore() {
        return score;
    }
}
