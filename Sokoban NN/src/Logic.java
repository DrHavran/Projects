
import NN.ReplayMemory;
import NN.RewardSystem;
import Object.*;

import java.util.ArrayList;
import java.util.Collections;

import static Object.Direction.directions;

public class Logic {

    private final ArrayList<ReplayMemory> memories;
    private final RewardSystem reward;

    public Logic() {
        this.memories = new ArrayList<>();
        this.reward = new RewardSystem();
        generateReplayMemory(20);
        memories.forEach(System.out::println);
    }

    private void generateReplayMemory(int amount){
        Level level = new Level();

        double epsilon = 1;
        boolean randomMove = Math.random() < epsilon;

        for(int i = 0; i < amount; i++){
            Direction direction = null;

            if(randomMove){
                ArrayList<Direction> directions = Direction.directions;
                Collections.shuffle(directions);

                for(Direction d : directions){
                    if(level.isValidMove(d)){
                        direction = d;
                        break;
                    }
                }
                if(direction == null){
                    throw new NoValidMoves("There aren't any valid moves");
                }
            }else{


                for(Direction d : directions){

                }
            }

            memories.add(level.move(direction));
        }
    }
}