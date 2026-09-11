package org.example.PathFinder;

import org.example.GlobalSettings;
import org.example.Models.Node;

public class BFS extends PathFinder {

    public BFS() {
        super();
    }

    public void findPath(Node start, Node end) {
        queue.add(start);
        visited.add(start);
        start.setParent(null);

        while (!queue.isEmpty()) {
            Node selected = queue.removeFirst();

            for(Node node : selected.getAdjacentNodes()){
                steps++;
                if(!visited.contains(node)){
                    node.setParent(selected);
                    visited.add(node);
                    if(node == end){
                        if(GlobalSettings.printPathfinderResults){
                            System.out.println("found end");
                            System.out.println("BFS took " + steps + " steps");
                        }
                        createPath(node);
                        return;
                    } else {
                        queue.add(node);
                    }
                }
            }
        }
        if(GlobalSettings.printPathfinderResults){
            System.out.println("Didnt find a path");
        }
    }
}
