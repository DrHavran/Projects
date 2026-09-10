package org.example.PathFinder;

import org.example.Models.Node;
import org.example.Models.Path;

public class AStar extends PathFinder {

    public AStar() {
        super();
    }

    @Override
    public void findPath(Node start, Node end){
        cleanLists();

        start.setValue(0);
        start.setParent(null);
        priorityQueue.add(start);

        while(!priorityQueue.isEmpty()){
            Node selected = priorityQueue.poll();
            for(Path path : selected.getPaths()){
                Node node = path.getOtherNode(selected);
                if(node == end){
                    node.setParent(selected);
                    createPath(node);
                    System.out.println("found end");
                    System.out.println("A* took " + steps + " steps");
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
        System.out.println("Didnt find a path");
    }
}
