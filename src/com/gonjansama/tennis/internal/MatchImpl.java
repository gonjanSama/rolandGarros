package com.gonjansama.tennis.internal;


import com.gonjansama.tennis.Match;
import com.gonjansama.tennis.Player;

public class MatchImpl implements Match {
    private final Player player1;
    private final Player player2;
    private final SetImpl set;

    public MatchImpl(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        set = new SetImpl(this);
    }


    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    @Override
    public Player getWinner() {
        return set.getWinner();
    }

    public SetImpl getCurrentSet() {
        return set;
    }

    @Override
    public Player getPlayer(String name) {
        if (player1.getName().equalsIgnoreCase(name)) return player1;
        if (player2.getName().equalsIgnoreCase(name)) return player2;
        throw new IllegalArgumentException("No such player with name : " + name);
    }
}
