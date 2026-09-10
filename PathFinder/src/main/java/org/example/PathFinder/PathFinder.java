package org.example.PathFinder;

import org.example.Models.Node;
import org.example.Models.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class PathFinder {

    protected final ArrayList<Node> queue;
    protected final ArrayList<Node> visited;
    protected final PriorityQueue<Node> priorityQueue = new PriorityQueue<>(
            Comparator.comparingDouble(Node::getValue)
    );

    /**
     * Results of the pathfinder get stored here
     */
    protected final ArrayList<Node> nodes;
    protected final ArrayList<Path> path;
    protected int steps;

    public PathFinder() {
        this.path = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.queue = new ArrayList<>();
        this.visited = new ArrayList<>();
        this.steps = 0;
    }

    protected void cleanLists(){
        queue.clear();
        visited.clear();
        nodes.clear();
        path.clear();
        steps = 0;
    };

    public abstract void findPath(Node start, Node end);

    protected void createPath(Node node){
        Node selected = node;

        while(selected.getParent() != null){
            path.add(new Path(selected, selected.getParent()));
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
    public ArrayList<Path> getPath() {
        return path;
    }
    public int getSteps() {
        return steps;
    }
}