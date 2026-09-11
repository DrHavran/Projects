package org.example.PathFinder;

import org.example.GlobalSettings;
import org.example.Models.Node;
import org.example.Models.Path;

public class DSA extends PathFinder {

    public DSA() {
        super();
    }

    @Override
    public void findPath(Node start, Node end){
        start.setValue(0);
        visited.add(start);
        priorityQueue.add(start);

        while(!priorityQueue.isEmpty()){
            Node selected = priorityQueue.poll();
            for(Node node : selected.getAdjacentNodes()){
                Path path = selected.getPaths().get(node);
                if(node == end){
                    if(GlobalSettings.printPathfinderResults){
                        System.out.println("found end");
                        System.out.println("DSA took " + steps + " steps");
                    }
                    node.setParent(selected);
                    createPath(node);
                    return;
                }

                if(!visited.contains(node)){
                    steps++;
                    double distance = path.getDistance();
                    if(selected.getValue() + distance < node.getValue()){
                        node.setValue(selected.getValue() + distance);
                        node.setParent(selected);
                        priorityQueue.add(node);
                    }
                }
            }
            visited.add(selected);
        }

        if(GlobalSettings.printPathfinderResults){
            System.out.println("Didnt find a path");
        }
    }
}
