package org.example.PathFinder;

import org.example.Models.Node;
import org.example.Models.Path;

import java.util.*;

public abstract class PathFinder {

    protected final ArrayList<Node> queue;
    protected HashSet<Node> visited;
    protected final PriorityQueue<Node> priorityQueue = new PriorityQueue<>(
            Comparator.comparingDouble(Node::getValue)
    );

    /**
     * Results of the pathfinder get stored here
     */
    protected final ArrayList<Node> nodes;
    protected final ArrayList<Path> fullPath;
    protected int steps;
    protected double pathLength;

    public PathFinder() {
        this.fullPath = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.queue = new ArrayList<>();
        this.visited = new HashSet<>();
    }

    public abstract void findPath(Node start, Node end);

    protected void createPath(Node node){
        Node selected = node;

        while(selected.getParent() != null){
            Path path = selected.getPaths().get(selected.getParent());
            pathLength += path.getDistance();
            fullPath.add(path);
            selected = selected.getParent();
        }
    }

    protected double calculateDistance(Node start, Node end){
        double lonDifference = start.getLongitude() - end.getLongitude();
        double lanDifference = start.getLatitude() - end.getLatitude();
        return Math.sqrt(Math.pow(lonDifference, 2) + Math.pow(lanDifference, 2));
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
    public ArrayList<Path> getFullPath() {
        return fullPath;
    }
    public int getSteps() {
        return steps;
    }
    public double getPathLength() {
        return pathLength;
    }
}