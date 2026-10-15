import java.util.HashSet;
import java.util.Objects;

public class Logic {
    private final Data data;
    private final HashSet<Cluster> clusters;

    public Logic() {
        this.clusters = new HashSet<>();
        this.data = new Data();
        int bestScore = 0;

        for(int i = 0; i < Settings.rounds; i++){
            createClusters();
            runKMeans();

            int score = test();
            if(bestScore < score){
                bestScore = score;
            }
            clear();
        }
        System.out.println("Best score: " + bestScore);
    }

    // K-means++
    public void createClusters() {
        Node randomNode = getRandomNode();
        clusters.add(new Cluster(randomNode));

        while (clusters.size() < Settings.clusters) {
            double totalDistance = 0;
            double[] distances = new double[data.getNodes().size()];

            for (int i = 0; i < data.getNodes().size(); i++) {
                Node node = data.getNodes().get(i);
                double minDistance = Double.MAX_VALUE;

                for (Cluster cluster : clusters) {
                    double distance = calculateDistance(cluster, node);
                    minDistance = Math.min(minDistance, distance);
                }

                distances[i] = minDistance * minDistance;
                totalDistance += distances[i];
            }

            double random = Math.random() * totalDistance;
            double cumulativeDistance = 0;
            Node bestNode = getRandomNode();

            for (int i = 0; i < distances.length; i++) {
                cumulativeDistance += distances[i];

                if (random < cumulativeDistance) {
                    bestNode = data.getNodes().get(i);
                    break;
                }
            }

            clusters.add(new Cluster(bestNode));
        }
    }

    private void runKMeans(){
        boolean movement;

        do{
            movement = false;

            for(Node node : data.getNodes()) {
                for(Cluster cluster : clusters) {
                    calculateNode(cluster, node, true);
                }
            }

            // Move the clusters based on the new data
            for(Cluster cluster : clusters) {
                if(cluster.step()){
                    movement = true;
                }
            }
        }while(movement);
    }

    private int test(){
        int solved = 0;
        for(Node node : data.getTestNodes()) {
            for(Cluster cluster : clusters) {
                calculateNode(cluster, node, false);
            }

            // Check if the node got assigned correctly
            if(Objects.equals(node.getName(), node.getClosestCluster().getMajority())){
                solved++;
            }
        }
        System.out.println("Solved: " + solved + "/" + data.getTestNodes().size());
        return solved;
    }

    private void calculateNode(Cluster cluster, Node node, boolean addNodeToCluster){
        double distance = calculateDistance(cluster, node);

        if(distance < node.getClusterDistance()) {
            Cluster currentCluster = node.getClosestCluster();
            if(addNodeToCluster){
                if(currentCluster != null) {
                    currentCluster.removeNode(node);
                }
                cluster.addNode(node);
            }

            node.setClusterDistance(distance);
            node.setClosestCluster(cluster);
        }
    }
    private double calculateDistance(Cluster cluster, Node node){
        double distance = 0;
        for(int i = 0; i < node.getValuesSize(); i++){
            distance += Math.pow(Math.abs(cluster.getValues()[i] - node.getValues()[i]), 2);
        }

        return Math.sqrt(distance);
    }

    private void clear(){
        clusters.clear();
        for(Node node : data.getNodes()){
            node.reset();
        }
    }
    private Node getRandomNode(){
        return data.getNodes().get(
                (int) (Math.random() * data.getNodes().size())
        );
    }
}