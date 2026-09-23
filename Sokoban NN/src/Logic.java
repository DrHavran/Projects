
import Level.Level;
import Level.Box;

public class Logic {

    private final Level level;

    public Logic() {
        this.level = new Level();
    }

    public void move(String move){
        level.move(move);
    }
    public boolean isSolved(){
        for(Box box : level.getBoxes()){
            if(!box.checkGoal(level.getGoals())) return false;
        }
        return true;
    }
    public Level getLevel() {
        return level;
    }
}
