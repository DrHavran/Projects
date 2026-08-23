package org.example;

import java.util.HashSet;

public class Node {
    private final double longitude; //X
    private final double latitude;  //Y
    private final HashSet<Node> paths;

    private double value;
    private double fromStart;

    private Node parent;

    public Node(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.value = Double.MAX_VALUE;
        this.paths = new HashSet<>();
        this.parent = null;
    }

    public void addNode(Node node) {
        paths.add(node);
    }

    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public HashSet<Node> getPaths() { return paths; }

    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }

    public double getFromStart() {
        return fromStart;
    }
    public void setFromStart(double fromStart) {
        this.fromStart = fromStart;
    }

    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
}