package org.example.Logic.PathFinding;

import org.example.Node;
import org.example.Path;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public abstract class PathFinder {
    protected final ArrayList<Path> finalPath;
    protected int steps;

    protected final HashSet<Node> visited;
    protected final ArrayList<Node> queue;
    protected final PriorityQueue<Node> priorityQueue;

    protected final HashSet<Node> toClear;

    public PathFinder() {
        this.finalPath = new ArrayList<>();
        this.visited = new HashSet<>();
        this.queue = new ArrayList<>();
        this.priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(Node::getValue));
        this.toClear = new HashSet<>();
        this.steps = 0;
    }

    public abstract void findPath(Node start, Node end);

    protected void clean(){
        finalPath.clear();
        visited.clear();
        priorityQueue.clear();
        queue.clear();
        for(Node node : toClear){
            node.setValue(Double.MAX_VALUE);
            node.setParent(null);
            node.setFromStart(0);
        }
        toClear.clear();
        steps = 0;
    }

    protected double calculateDistance(Node start, Node end){
        double lon = start.getLongitude() - end.getLongitude();
        double lan = start.getLatitude() - end.getLatitude();
        return Math.sqrt(Math.pow(lon, 2) + Math.pow(lan, 2));
    }

    protected void createPath(Node node){
        Node selected = node;

        while(selected.getParent() != null){
            finalPath.add(new Path(selected, selected.getParent()));
            selected = selected.getParent();
        }
    }

    public double getLength(){
        double total = 0;
        for(Path p : finalPath){
            total += p.getLength();
        }
        return total;
    }
    public int getSteps(){
        return this.steps;
    }
    public ArrayList<Path> getFinalPath(){
        return finalPath;
    }
}
