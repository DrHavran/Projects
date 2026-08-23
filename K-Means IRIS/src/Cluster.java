import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Cluster {
    private final Double[] values;
    private final HashSet<Node> nodes;
    private String majority;
    private boolean moved;

    public Cluster(Double[] values) {
        this.values = values;
        this.nodes = new HashSet<>();
        this.moved = false;
    }

    public void average() {
        moved = false;
        Double[] totals = new Double[values.length];
        Arrays.fill(totals, 0.0);

        for(Node node : nodes) {
            for(int i = 0; i < values.length; i++) {
                totals[i] += node.getValues()[i];
            }
        }

        Double[] newValues = new Double[values.length];
        for(int i = 0; i < values.length; i++) {
            newValues[i] = totals[i] / nodes.size();
            newValues[i] = (double) (int) (newValues[i] * Settings.decimalAccuracy);
            newValues[i] = newValues[i] / Settings.decimalAccuracy;

            if(!Objects.equals(newValues[i], values[i])){
                moved = true;
                values[i] = newValues[i];
            }
        }
    }

    public void majority(){
        HashMap<String, Integer> counts = new HashMap<>();
        for(Node node : nodes) {
            if(counts.containsKey(node.getName())) {
                counts.put(node.getName(), counts.get(node.getName()) + 1);
            }else{
                counts.put(node.getName(), 1);
            }
        }

        String maxName = null;
        int maxCount = 0;

        for(var entry : counts.entrySet()) {
            if(entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxName = entry.getKey();
            }
        }
        majority = maxName;
        System.out.println(maxName + " with " + maxCount + " nodes");
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }
    public void addNode(Node node) {
        this.nodes.add(node);
    }
    public boolean isMoved() {
        return moved;
    }
    public Double[] getValues() {
        return values;
    }
    public String getMajority() {
        return majority;
    }
}
