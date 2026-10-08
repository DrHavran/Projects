package Object;

import NN.ReplayMemory;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Level {
    private final int id;
    private final Player player;
    private final ArrayList<Box> boxes;
    private final int[][] walls;
    private final int[][] goals;

    public Level(int id, Player player, ArrayList<Box> boxes, int[][] walls, int[][] goals) {
        this.id = id;
        this.player = player;
        this.boxes = boxes;
        this.walls = walls;
        this.goals = goals;
    }

    public Level() {
        this.id = 0;

        // Player at (1,1)
        this.player = new Player(1, 1);

        // One box at (2,1)
        this.boxes = new ArrayList<>();
        this.boxes.add(new Box(2, 2));

        // 5x5 board. walls[y][x] = 1 means wall.
        this.walls = new int[][] {
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1}
        };

        // goals[y][x] = 1 means goal
        this.goals = new int[][] {
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0}
        };
    }

    public boolean isValidMove(Direction direction){
        int nX = player.getX() + direction.dx();
        int nY = player.getY() + direction.dy();

        // Check wall
        if(walls[nY][nX] == 1) return false;

        Box box = checkBox(nX, nY);
        if(box != null){
            int bnX = box.getX() + direction.dx();
            int bnY = box.getY() + direction.dy();

            if(walls[bnY][bnX] == 1){
                return false;
            }else{
                Box secondBox = checkBox(bnX, bnY);
                return secondBox == null;
            }
        }
        return true;
    }

    public ReplayMemory move(Direction direction){
        String startKey = getKey();

        player.setX(player.getX() + direction.dx());
        player.setY(player.getY() + direction.dy());

        Box box = checkBox(player.getX(), player.getY());
        if(box != null){
            box.setX(box.getX() + direction.dx());
            box.setY(box.getY() + direction.dy());
        }

        String endKey = getKey();

        return new ReplayMemory(
                startKey,
                direction,
                0.0,
                endKey,
                false
        );
    }

    private Box checkBox(int x, int y){
        return boxes.stream()
                .filter(box -> box.getX() == x && box.getY() == y)
                .findFirst()
                .orElse(null);
    }

    public int getId() {
        return id;
    }
    public int[][] getWalls() {
        return walls;
    }
    public int[][] getGoals() {
        return goals;
    }
    public String getKey(){
        ArrayList<String> boxKeys = boxes.stream()
                .map(Box::getKey)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));

        return id + "|" + player.getKey() + "|" + boxKeys;
    }
}
