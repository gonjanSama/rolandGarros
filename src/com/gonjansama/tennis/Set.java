package com.gonjansama.tennis;

public interface Set {
    Integer getPlayerScore(Player player);
    Integer getPlayerTieBreakScore(Player player);
    Game createGame();
    Game getCurrentGame();
    Player getPlayer1();
    Player getPlayer2();
    Player getPlayer(String name);
    Player getWinner();
    Boolean isOnTieBreak();
}
