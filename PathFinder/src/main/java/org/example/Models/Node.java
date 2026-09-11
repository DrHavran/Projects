package org.example.Models;

import java.util.HashMap;
import java.util.Set;

public class Node {
    private final String id;
    private final double longitude, latitude;
    private final HashMap<Node, Path> paths;

    /**
     * Variables used for harder pathfinding algorithms
     */
    private Node parent;
    private double value;
    private double totalValue;

    public Node(String id, double longitude, double latitude) {
        this.value = Double.MAX_VALUE;
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.paths = new HashMap<>();
    }

    public void reset() {
        parent = null;
        value = Double.MAX_VALUE;
        totalValue = 0;
    }

    public void addPath(Node node, Path path){
        paths.put(node, path);
    }

    public Set<Node> getAdjacentNodes() {
        return paths.keySet();
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public Node getParent() {
        return parent;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public HashMap<Node, Path> getPaths() {
        return paths;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public double getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
}