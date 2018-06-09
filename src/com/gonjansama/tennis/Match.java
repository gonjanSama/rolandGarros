package com.gonjansama.tennis;

public interface Match {
    Player getPlayer(String name);
    Player getWinner();
    Player getPlayer1();
    Player getPlayer2();
    Set getCurrentSet();
}
