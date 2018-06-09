package com.gonjansama.tennis;

public interface Game {
    String getPlayerScore(Player player);
    Player getPlayer(String name);
    Boolean isOnDeuce();
    Player getWinner();
    void addPoint(Player player);
}
