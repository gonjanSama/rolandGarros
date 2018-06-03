package com.gonjansama.rolandgarros;

public class GamePointTransition {
    private final GamePoint from;
    private final GamePoint to;
    private final Rule rule;

    GamePointTransition(GamePoint from, GamePoint to, Rule rule) {
        this.from = from;
        this.to = to;
        this.rule = rule;
    }

    public Rule getRule() {
        return rule;
    }

    GamePoint getFrom() {
        return from;
    }

    GamePoint getTo() {
        return to;
    }
}
