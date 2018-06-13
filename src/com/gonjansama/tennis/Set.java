package com.gonjansama.tennis;

import java.util.Optional;

public interface Set {
    Integer getPlayerScore(Player player);
    void setPlayerScore(Player player, Integer playerScore);
    Integer getPlayerTieBreakScore(Player player);
    void setPlayerTieBreakScore(Player player, Integer playerScore);
    Integer getPlayer1Score();
    Integer getPlayer2Score();
    Boolean isInProgress();
    Game createGame();
    void updateScore();
    Player getOpponent(Player player);
    Boolean hasWinner();
    Game getCurrentGame();
    void setCurrentGame(Game game);
    Player getPlayer1();
    Player getPlayer2();
    Player getPlayer(String name);
    Optional<Player> getWinner();
    Boolean isOnTieBreak();
}
