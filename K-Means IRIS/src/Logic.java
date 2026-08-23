import java.util.HashSet;
import java.util.Objects;

public class Logic {
    private final Data data;

    public Logic() {
        this.data = new Data();

        HashSet<Cluster> bestClusters = new HashSet<>();
        int bestScore = 0;

        for(int i = 0; i < Settings.rounds; i++){
            data.createClusters();
            round();
            for(Cluster cluster : data.getClusters()) {
                cluster.majority();
            }
            int score = checkTest();
            if(bestScore < score){
                bestScore = score;
                bestClusters = data.getClusters();
            }
            clear();
        }
        System.out.println("Best score: " + bestScore);
    }

    private void clear(){
        data.getClusters().clear();
        for(Node node : data.getNodes()){
            node.reset();
        }
    }

    private void round(){
        boolean movement;

        do{
            movement = false;
            step();
            for(Cluster cluster : data.getClusters()) {
                if (cluster.isMoved()) {
                    movement = true;
                    break;
                }
            }
        }while(movement);
    }

    private void step() {
        for(Node node : data.getNodes()) {
            for(Cluster cluster : data.getClusters()) {
                double distance = 0;
                for(int i = 0; i < node.getSize(); i++){
                    distance += Math.pow(Math.abs(cluster.getValues()[i] - node.getValues()[i]), 2);
                }

                distance = Math.sqrt(distance);

                if(distance < node.getClusterDistance()) {
                    Cluster currentCluster = node.getClosestCluster();
                    if(currentCluster != null) {
                        currentCluster.removeNode(node);
                    }

                    node.setClusterDistance(distance);
                    node.setClosestCluster(cluster);

                    cluster.addNode(node);
                }
            }
        }

        for(Cluster cluster : data.getClusters()) {
            cluster.average();
        }
    }

    private int checkTest(){
        int solved = 0;
        for(Node node : data.getTestNodes()) {
            for(Cluster cluster : data.getClusters()) {
                double distance = 0;
                for(int i = 0; i < node.getSize(); i++){
                    distance += Math.pow(Math.abs(cluster.getValues()[i] - node.getValues()[i]), 2);
                }

                distance = Math.sqrt(distance);

                if(distance < node.getClusterDistance()) {
                    Cluster currentCluster = node.getClosestCluster();
                    if(currentCluster != null) {
                        currentCluster.removeNode(node);
                    }

                    node.setClusterDistance(distance);
                    node.setClosestCluster(cluster);
                }
            }
            if(Objects.equals(node.getName(), node.getClosestCluster().getMajority())){
                solved++;
            }
        }
        System.out.println("Solved: " + solved + "/" + data.getTestNodes().size());
        return solved;
    }

}