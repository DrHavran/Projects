import java.util.*;
import java.util.stream.Collectors;

public class Cluster {
    private Double[] values;
    private final HashSet<Node> nodes;

    public Cluster(Node node) {
        this.values = node.getValues().clone();
        this.nodes = new HashSet<>();
    }

    // Moves the cluster depending on the average of all the nodes inside
    public boolean step() {
        Double[] averageValues = new Double[values.length];
        Arrays.fill(averageValues, 0.0);

        for(Node node : nodes) {
            for(int i = 0; i < values.length; i++) {
                averageValues[i] += roundToPlace(
                        node.getValues()[i] / nodes.size()
                );
            }
        }

        if(!Arrays.equals(averageValues, values)){
            this.values = averageValues;
            return true;
        }
        return false;
    }

    private double roundToPlace(Double number){
        return Math.round(number * Math.pow(10, Settings.decimalAccuracy)) / Math.pow(10, Settings.decimalAccuracy);
    }

    public void removeNode(Node node) {
        this.nodes.remove(node);
    }
    public void addNode(Node node) {
        this.nodes.add(node);
    }
    public Double[] getValues() {
        return values;
    }
    public String getMajority() {
        return nodes.stream()
                .collect(Collectors.groupingBy(Node::getName, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}