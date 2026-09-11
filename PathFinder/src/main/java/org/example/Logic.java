package org.example;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.Draw.Draw;
import org.example.Models.Node;
import org.example.PathFinder.*;

import java.util.Objects;
import java.util.Set;

public class Logic {
    private Draw draw;
    private final Data data;

    public Logic() {
        this.data = new Data();
    }

    public void findCenter(){
        Node bestNode = null;
        double bestValue = Double.MAX_VALUE;

        Set<String> nodeIds = data.getNodes().keySet();
        int count = 1;

        for(String testNode : nodeIds){
            System.out.println("Testing node " + count + "/" + nodeIds.size());
            double biggestValue = 0.0;
            int innerCount = 1;

            for(String endNode : data.getNodes().keySet()){
                if(Objects.equals(testNode, endNode)){
                    continue;
                }
                System.out.println("    " + innerCount + "/" + (nodeIds.size() - 1));
                PathFinder pathFinder = findPath(testNode, endNode, false);
                if(biggestValue < pathFinder.getPathLength()){
                    biggestValue = pathFinder.getPathLength();
                }
                innerCount++;
            }
            System.out.println("Node " + count + " has a value of " + biggestValue);
            if(biggestValue < bestValue){
                System.out.println("That's the new best score!!");
                bestValue = biggestValue;
                bestNode = data.getNode(testNode);
            }
            count++;
        }

        if(bestNode != null){
            draw.drawNode(bestNode, Color.RED, 3);
        }else{
            System.out.println("Couldn't find a center");
        }
    }

    public PathFinder findPath(String startId, String endId, boolean drawPath){
        data.getNodes().values().forEach(Node::reset);
        PathFinder pathFinder = getPathFinder(GlobalSettings.pathFindingAlgorithm);
        pathFinder.findPath(data.getNode(startId), data.getNode(endId));

        if(drawPath){
            pathFinder.getNodes().forEach(node -> draw.drawNode(node, Color.RED, 1));
            pathFinder.getFullPath().forEach(path -> draw.drawLine(path, Color.RED, 1));
        }
        return pathFinder;
    }

    public PathFinder getPathFinder(String name){
        return switch (name) {
            case "A*" -> new AStar();
            case "BFS"   -> new BFS();
            case "DFS"   -> new DFS();
            case "DSA"   -> new DSA();
            default -> throw new IllegalArgumentException("Unknown algorithm: " + name);
        };
    }
    public void drawMap(){
        draw.drawMap();
    }
    public Pane getRoot(){
        return draw.getRoot();
    }
    public void loadFile(String file){
        data.loadFile(file);
        draw = new Draw(this.data);
    }
}
