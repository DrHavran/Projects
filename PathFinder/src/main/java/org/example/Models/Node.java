package org.example.Models;

import java.util.ArrayList;

public class Node {
    private final String id;
    private final double longitude, latitude;
    private final ArrayList<Path> paths;

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
        this.paths = new ArrayList<>();
    }

    public void addPath(Path path){
        paths.add(path);
    }

    public ArrayList<Node> getAdjacentNodes() {
        ArrayList<Node> adjacentNodes = new ArrayList<>(paths.size() * 2);

        for (Path path : paths) {
            Node start = path.getStart();
            if (start != this) {
                adjacentNodes.add(start);
            }

            Node end = path.getEnd();
            if (end != this) {
                adjacentNodes.add(end);
            }
        }

        return adjacentNodes;
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
    public ArrayList<Path> getPaths() {
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