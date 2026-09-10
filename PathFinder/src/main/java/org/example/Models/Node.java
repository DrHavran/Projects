package org.example.Models;

import java.util.ArrayList;

public class Node {
    private final String id;
    private final double longitude, latitude;
    private final ArrayList<Path> paths;

    private Node parent;

    public Node(String id, double longitude, double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.paths = new ArrayList<>();
    }

    public void addPath(Path path){
        paths.add(path);
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
}