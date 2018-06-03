package com.gonjansama.rolandgarros;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game {
    private Integer id;
    private final Player player1;
    private final Player player2;
    private Map<Player, GamePoint> score = new HashMap<>();

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.id = new Random().nextInt();
        score.put(player1, GamePoint.ZERO);
        score.put(player2, GamePoint.ZERO);
    }


    public Integer getId() {
        return id;
    }

    public GamePoint getPlayer1Score() {
        return getScore().get(player1);
    }

    public GamePoint getPlayer2Score() {
        return getScore().get(player2);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Map<Player, GamePoint> getScore() {
        return score;
    }
}
