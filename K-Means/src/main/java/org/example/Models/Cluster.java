package org.example.Models;

import org.example.Settings;

import java.util.HashSet;

public class Cluster {
    private double x, y;
    private final HashSet<Node> nodes;

    public Cluster(double x, double y) {
        this.nodes = new HashSet<>();
        this.x = x;
        this.y = y;
    }

    public boolean step() {
        double totalX = 0;
        double totalY = 0;

        for(Node node : nodes) {
            totalX += node.getX();
            totalY += node.getY();
        }

        double newX = roundToPlace(totalX / nodes.size());
        double newY = roundToPlace(totalY / nodes.size());

        if (newX != x || newY != y) {
            x = newX;
            y = newY;
            return true;
        }
        return false;
    }

    private double roundToPlace(Double number){
        return Math.round(number * Math.pow(10, Settings.decimalAccuracy)) / Math.pow(10, Settings.decimalAccuracy);
    }
    public void removeNode(Node node) {
        nodes.remove(node);
    }
    public void addNode(Node node) {
        this.nodes.add(node);
    }
    public HashSet<Node> getNodes() {
        return nodes;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
