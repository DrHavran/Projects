package Level;

public class Box {
    private int x,y;

    public Box(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean checkGoal(int[][] goals){
        return goals[y][x] == 1;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
}
