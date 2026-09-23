package Level;

import java.util.ArrayList;

public class Generator {
    private Player player;
    private final ArrayList<Box> boxes;
    private int[][] walls, goals;

    public Generator() {
        this.boxes = new ArrayList<>();
        generateALevel();
    }

    private void generateALevel() {
        String[] map = {
                "########",
                "#      #",
                "#  .   #",
                "#  B   #",
                "#  P   #",
                "#      #",
                "########"
        };

        int height = map.length;
        int width  = map[0].length();

        walls = new int[height][width];
        goals = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = map[y].charAt(x);
                switch (c) {
                    case '#' -> walls[y][x] = 1;
                    case '.' -> goals[y][x] = 1;
                    case 'P' -> player = new Player(x, y);
                    case 'B' -> boxes.add(new Box(x, y));
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }
    public ArrayList<Box> getBoxes() {
        return boxes;
    }
    public int[][] getWalls() {
        return walls;
    }
    public int[][] getGoals() {
        return goals;
    }
}