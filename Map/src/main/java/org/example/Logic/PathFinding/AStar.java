package org.example.Logic.PathFinding;

import org.example.Logic.Settings;
import org.example.Node;

public class AStar extends PathFinder {

    public AStar() {
        super();
    }

    @Override
    public void findPath(Node start, Node end){
        clean();

        start.setValue(0);
        start.setParent(null);
        toClear.add(start);
        priorityQueue.add(start);

        while(!priorityQueue.isEmpty()){
            Node selected = priorityQueue.poll();
            for(Node node : selected.getPaths()){
                if(node == end){
                    node.setParent(selected);
                    createPath(node);
                    if(Settings.print){
                        System.out.println("found end");
                        System.out.println("A* took " + steps + " steps");
                    }
                    return;
                }

                if(!visited.contains(node)){
                    steps++;
                    double fromStart = selected.getFromStart() + calculateDistance(selected, node);
                    double fromEnd = calculateDistance(node, end);

                    if(node.getValue() > fromStart + fromEnd){
                        node.setValue(fromStart + fromEnd);
                        node.setFromStart(fromStart);
                        node.setParent(selected);
                        toClear.add(node);
                        priorityQueue.add(node);
                    }
                }
            }
            visited.add(selected);
        }
        System.out.println("Didnt find a path");
    }
}
