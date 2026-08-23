public class Node {
    private final Double[] values;
    private final String name;
    private double clusterDistance;
    private Cluster closestCluster;

    public Node(String name, Double[] values) {
        this.values = values;
        this.name = name;

        reset();
    }

    public void reset(){
        clusterDistance = Double.MAX_VALUE;
        closestCluster = null;
    }
    public void setClosestCluster(Cluster closestCluster) {
        this.closestCluster = closestCluster;
    }
    public void setClusterDistance(double clusterDistance) {
        this.clusterDistance = clusterDistance;
    }
    public Cluster getClosestCluster() {
        return closestCluster;
    }
    public double getClusterDistance() {
        return clusterDistance;
    }
    public Double[] getValues() {
        return values;
    }
    public int getSize() {
        return values.length;
    }
    public String getName(){
        return name;
    }
}
