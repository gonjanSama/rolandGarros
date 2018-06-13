package com.gonjansama.tennis;

import java.util.Optional;

public interface Match {
    Player getPlayer(String name);
    Optional<Player> getWinner();
    Player getPlayer1();
    Player getPlayer2();
    Set getCurrentSet();
}
