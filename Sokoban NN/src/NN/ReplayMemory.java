package NN;

import Object.*;

public class ReplayMemory {
    private final String keyBefore;
    private final Direction direction;
    private final double value;
    private final String keyAfter;
    private final boolean levelFinished;

    public ReplayMemory(String keyBefore, Direction direction, double value, String keyAfter, boolean levelFinished) {
        this.keyBefore = keyBefore;
        this.direction = direction;
        this.value = value;
        this.keyAfter = keyAfter;
        this.levelFinished = levelFinished;
    }

    public String getKeyBefore() {
        return keyBefore;
    }
    public Direction getDirection() {
        return direction;
    }
    public double getValue() {
        return value;
    }
    public String getKeyAfter() {
        return keyAfter;
    }
    public boolean isLevelFinished() {
        return levelFinished;
    }

    @Override
    public String toString() {
        return "ReplayMemory{" +
                "keyBefore='" + keyBefore + '\'' +
                ", direction=" + direction +
                ", value=" + value +
                ", keyAfter='" + keyAfter + '\'' +
                ", levelFinished=" + levelFinished +
                '}';
    }
}