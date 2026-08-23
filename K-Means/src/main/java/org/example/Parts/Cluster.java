package org.example.Parts;

import org.example.Settings;

import java.util.HashSet;

public class Cluster {
    private double x, y;
    private final HashSet<Node> nodes;
    private boolean moved;

    public Cluster(double x, double y) {
        this.nodes = new HashSet<>();
        this.x = x;
        this.y = y;
        this.moved = false;
    }

    public void average() {
        double totalX = 0;
        double totalY = 0;

        for(Node node : nodes) {
            totalX += node.getX();
            totalY += node.getY();
        }

        double newX = totalX / nodes.size();
        double newY = totalY / nodes.size();

        newX = (double) (int) newX * 10 * Settings.decimalAccuracy;
        newY = (double) (int) newY * 10 * Settings.decimalAccuracy;

        newX = newX / (10 * Settings.decimalAccuracy);
        newY = newY / (10 * Settings.decimalAccuracy);

        if (newX != x || newY != y) {
            moved = true;
            x = newX;
            y = newY;
        } else {
            moved = false;
        }
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
    public boolean isMoved() {
        return moved;
    }
}
