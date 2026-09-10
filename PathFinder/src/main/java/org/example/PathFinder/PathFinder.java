package org.example.PathFinder;

import org.example.Models.Node;
import org.example.Models.Path;
import java.util.ArrayList;

public abstract class PathFinder {

    protected final ArrayList<Node> nodes;
    protected final ArrayList<Path> path;
    protected int steps;

    public PathFinder() {
        this.path = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.steps = 0;
    }

    public abstract void findPath(Node start, Node end);

    protected void createPath(Node node){
        Node selected = node;

        while(selected.getParent() != null){
            path.add(new Path(selected, selected.getParent()));
            selected = selected.getParent();
        }
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