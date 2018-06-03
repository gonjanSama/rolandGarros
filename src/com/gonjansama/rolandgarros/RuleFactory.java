package com.gonjansama.rolandgarros;

public class RuleFactory {
    private static Rule standard = new StandardRule();

    public static <T extends Rule> Rule create(Class<T> className) {
        if (StandardRule.class.equals(className)) {
            return standard;
        }
        return null;
    }
}
