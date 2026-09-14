package org.example.PathFinder;

import org.example.Data;
import org.example.GlobalSettings;
import org.example.Models.Node;
import org.example.Models.Path;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class FindCenter {

    private final Data data;
    protected HashSet<Node> visited;
    protected final PriorityQueue<Node> priorityQueue = new PriorityQueue<>(
            Comparator.comparingDouble(Node::getValue)
    );

    public FindCenter(Data data) {
        this.data = data;
        this.visited = new HashSet<>();
    }

    public double calculateCenterValue(Node start){
        visited.clear();
        priorityQueue.clear();
        data.getNodes().values().forEach(Node::reset);

        start.setValue(0);
        visited.add(start);
        priorityQueue.add(start);

        while(!priorityQueue.isEmpty()){
            Node selected = priorityQueue.poll();
            for(Node node : selected.getAdjacentNodes()){
                Path path = selected.getPaths().get(node);
                if(!visited.contains(node)){
                    double distance = path.getDistance();
                    if(selected.getValue() + distance < node.getValue()){
                        node.setValue(selected.getValue() + distance);
                        priorityQueue.add(node);
                    }
                }
            }
            visited.add(selected);
        }

        return data.getNodes().values().stream()
                .mapToDouble(Node::getValue)
                .filter(value -> value != Double.MAX_VALUE)
                .max()
                .orElse(0.0);
    }
}
