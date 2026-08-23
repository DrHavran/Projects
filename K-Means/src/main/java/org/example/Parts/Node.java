package org.example.Parts;

public class Node {
    private double x, y;
    private double clusterDistance;
    private Cluster closestCluster;

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
        clusterDistance = Double.MAX_VALUE;
        closestCluster = null;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
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
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
