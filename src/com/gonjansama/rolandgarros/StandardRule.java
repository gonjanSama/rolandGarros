package com.gonjansama.rolandgarros;

public class StandardRule implements Rule {

    @Override
    public Boolean matches(Game game) {
        return  !(game.getPlayer1Score().equals(GamePoint.FOURTHY) &&
                game.getPlayer2Score().equals(GamePoint.FOURTHY));
    }
}
