package org.example.Logic.PathFinding;

import org.example.Logic.Settings;
import org.example.Node;

public class DSA extends PathFinder {

    public DSA() {
        super();
    }

    @Override
    public void findPath(Node start, Node end){
        clean();

        start.setValue(0);
        visited.add(start);
        priorityQueue.add(start);

        while(!priorityQueue.isEmpty()){
            Node selected = priorityQueue.poll();
            for(Node node : selected.getPaths()){
                if(node == end){
                    createPath(selected);
                    if(Settings.print){
                        System.out.println("found end");
                        System.out.println("DSA took " + steps + " steps");
                    }
                    return;
                }

                if(!visited.contains(node)){
                    steps++;
                    double distance = calculateDistance(selected, node);
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
