package org.example.PathFinder;

import org.example.GlobalSettings;
import org.example.Models.Node;
import org.example.Models.Path;

public class AStar extends PathFinder {

    public AStar() {
        super();
    }

    @Override
    public void findPath(Node start, Node end){
        start.setValue(0);
        start.setParent(null);
        priorityQueue.add(start);

        while(!priorityQueue.isEmpty()){
            Node selected = priorityQueue.poll();
            for(Node node : selected.getAdjacentNodes()){
                Path path = selected.getPaths().get(node);
                if(node == end){
                    node.setParent(selected);
                    createPath(node);
                    if(GlobalSettings.printPathfinderResults){
                        System.out.println("found end");
                        System.out.println("A* took " + steps + " steps");
                    }
                    return;
                }

                if(!visited.contains(node)){
                    steps++;
                    double totalValue = selected.getTotalValue() + path.getDistance();
                    double distanceToEnd = calculateDistance(node, end);

                    if(node.getValue() > totalValue + distanceToEnd){
                        node.setValue(totalValue + distanceToEnd);
                        node.setTotalValue(totalValue);
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
