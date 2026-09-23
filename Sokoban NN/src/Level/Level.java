package Level;

import java.util.ArrayList;

public class Level {

    private final Player player;
    private final ArrayList<Box> boxes;
    private final int[][] walls, goals;

    public Level() {
        Generator generator = new Generator();
        this.walls = generator.getWalls();
        this.goals = generator.getGoals();
        this.player = generator.getPlayer();
        this.boxes = generator.getBoxes();
    }

    public void move(String move) {
        int differenceX = 0, differenceY = 0;
        switch (move) {
            case "right" -> differenceX = 1;
            case "left"  -> differenceX = -1;
            case "up"    -> differenceY = -1;
            case "down"  -> differenceY = 1;
            default -> { return; }
        }

        int nextX = player.getX() + differenceX;
        int nextY = player.getY() + differenceY;

        // wall blocks
        if (walls[nextY][nextX] == 1) return;

        // box in the way?
        Box hit = boxAt(nextX, nextY);
        if (hit != null) {
            int behindX = nextX + differenceX;
            int behindY = nextY + differenceY;
            if (walls[behindY][behindX] == 1) return;   // wall behind box
            if (boxAt(behindX, behindY) != null) return; // another box behind
            hit.setX(behindX);
            hit.setY(behindY);
        }

        player.setX(nextX);
        player.setY(nextY);
    }

    private Box boxAt(int x, int y) {
        for (Box b : boxes) {
            if (b.getX() == x && b.getY() == y) return b;
        }
        return null;
    }

    public Player getPlayer() {
        return player;
    }
    public ArrayList<Box> getBoxes() {
        return boxes;
    }
    public int[][] getPlayerGrid() {
        int height = walls.length;
        int width = walls[0].length;
        int[][] grid = new int[height][width];
        grid[player.getY()][player.getX()] = 1;
        return grid;
    }
    public int[][] getBoxGrid() {
        int height = walls.length;
        int width = walls[0].length;
        int[][] grid = new int[height][width];
        for (Box b : boxes) {
            grid[b.getY()][b.getX()] = 1;
        }
        return grid;
    }
    public int[][] getWalls() {
        return walls;
    }
    public int[][] getGoals() {
        return goals;
    }
}