package org.example.PathFinder;

import org.example.Models.Node;
import org.example.Models.Path;

public class DSA extends PathFinder {

    public DSA() {
        super();
    }

    @Override
    public void findPath(Node start, Node end){
        cleanLists();

        start.setValue(0);
        visited.add(start);
        priorityQueue.add(start);

        while(!priorityQueue.isEmpty()){
            Node selected = priorityQueue.poll();
            for(Path path : selected.getPaths()){
                Node node = path.getOtherNode(selected);
                if(node == end){
                    createPath(selected);
                    System.out.println("found end");
                    System.out.println("DSA took " + steps + " steps");
                    createPath(end);
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
        System.out.println("Didnt find a path");
    }
}
