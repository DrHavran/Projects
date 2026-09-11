package org.example.PathFinder;


import org.example.GlobalSettings;
import org.example.Models.Node;
import org.example.Models.Path;

public class DFS extends PathFinder {

    private Node end;

    public DFS() {
        super();
    }

    public void findPath(Node start, Node end){
        this.end = end;
        nextStep(start);
    }

    private boolean nextStep(Node current){
        steps++;
        visited.add(current);
        for(Node node : current.getAdjacentNodes()){
            if(node == end){
                if(GlobalSettings.printPathfinderResults){
                    System.out.println("found end");
                    System.out.println("DFS took " + steps + " steps");
                }
                return true;
            }
            if (!visited.contains(node)) {
                if (nextStep(node)) {
                    fullPath.add(new Path(current, node));
                    return true;
                }
            }
        }
        return false;
    }
}
