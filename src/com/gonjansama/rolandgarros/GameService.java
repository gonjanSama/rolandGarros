package com.gonjansama.rolandgarros;

public interface GameService {
    Game createGame(String player1, String player2);

    void addPointToPlayer1(Game game);

    void addPointToPlayer2(Game game);
}
