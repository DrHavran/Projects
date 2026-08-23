package org.example.Logic.PathFinding;

import org.example.Logic.Settings;
import org.example.Node;
import org.example.Path;

public class DFS extends PathFinder {

    private Node end;

    public DFS() {
        super();
    }

    public void findPath(Node start, Node end){
        clean();
        this.end = end;
        nextStep(start);
    }

    private boolean nextStep(Node current){
        steps++;
        visited.add(current);
        for(Node node : current.getPaths()){
            if(node == end){
                if(Settings.print){
                    System.out.println("found end");
                    System.out.println("DFS took " + steps + " steps");
                }
                return true;
            }
            if (!visited.contains(node)) {
                if (nextStep(node)) {
                    finalPath.add(new Path(current, node));
                    return true;
                }
            }
        }
        return false;
    }
}
