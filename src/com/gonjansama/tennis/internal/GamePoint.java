package com.gonjansama.tennis.internal;

enum GamePoint {
    ZERO("0", 0), FIFTEEN("15", 15), THIRTHY("30", 30), FOURTHY("40", 40),
    ADVANTAGE("adv", 50), WIN_POINT("win_point", 99);

    private final String name;
    private final Integer weight;

    GamePoint(String name, Integer weight) {
        this.name = name;
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
