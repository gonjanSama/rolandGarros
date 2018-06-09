package com.gonjansama.tennis.internal;

class GamePointTransition {
    private final GamePoint from;
    private final GamePoint to;

    GamePointTransition(GamePoint from, GamePoint to) {
        this.from = from;
        this.to = to;
    }

    GamePoint getFrom() {
        return from;
    }

    GamePoint getTo() {
        return to;
    }
}
