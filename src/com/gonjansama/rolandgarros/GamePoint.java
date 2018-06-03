package com.gonjansama.rolandgarros;

public enum GamePoint {
    ZERO("0"), FIFTEEN("15"), THIRTHY("30"), FOURTHY("40"), WIN_POINT("win_point");

    private final String value;

    GamePoint(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
