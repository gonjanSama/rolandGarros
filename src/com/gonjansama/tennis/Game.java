package com.gonjansama.tennis;

import java.util.Optional;

public interface Game {
    String getPlayerScore(Player player);
    Player getPlayer(String name);
    Boolean isOnDeuce();
    Optional<Player> getWinner();
    Boolean hasWinner();
    Boolean isInProgress();
    void addPoint(Player player);
}
