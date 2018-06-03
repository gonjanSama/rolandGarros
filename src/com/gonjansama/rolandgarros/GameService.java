package com.gonjansama.rolandgarros;

public class GameService {
    public Game createGame(Player player1, Player player2) {
        return new Game(player1, player2);
    }
}
