package Object;

import java.util.ArrayList;
import java.util.Arrays;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public int dx() {
        return switch (this) {
            case LEFT -> -1;
            case RIGHT -> 1;
            default -> 0;
        };
    }

    public int dy() {
        return switch (this) {
            case UP -> -1;
            case DOWN -> 1;
            default -> 0;
        };
    }

    public static ArrayList<Direction> directions =
            new ArrayList<>(Arrays.asList(UP, DOWN, RIGHT, LEFT));
}