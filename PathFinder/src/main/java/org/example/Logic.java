package org.example;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.Draw.Draw;
import org.example.Models.Node;
import org.example.Models.Path;
import org.example.PathFinder.*;

import java.util.ArrayList;

public class Logic {
    private Draw draw;
    private final Data data;

    public Logic() {
        this.data = new Data();
    }

    public void findCenter(String nodeId){
        FindCenter findCenter = new FindCenter(data);
        ArrayList<Node> connectedNodes = findConnectedNodes(nodeId, true);
        Node bestNode = null;
        double bestValue = Double.MAX_VALUE;

        int count = 1;
        for(Node node : connectedNodes){
            double value = findCenter.calculateCenterValue(node);

            System.out.println("Node " + count +"/" + connectedNodes.size() + " has a value of " + value);
            if(value < bestValue){
                System.out.println("That's the new best score!!");
                bestValue = value;
                bestNode = node;
            }
            count++;
        }

        if(bestNode != null){
            System.out.println(bestNode.getId());
            draw.drawNode(bestNode, Color.GREEN, 4);
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

    public ArrayList<Node> findConnectedNodes(String nodeId, boolean drawNode){
        ArrayList<Node> queue = new ArrayList<>();
        ArrayList<Node> visited = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();

        Node start = data.getNode(nodeId);
        queue.add(start);

        while(!queue.isEmpty()){
            Node selected = queue.removeFirst();
            visited.add(selected);

            for(Path path : selected.getPaths().values()){
                Node node;

                if(path.getStart() == selected){
                    node = path.getEnd();
                }else{
                    node = path.getStart();
                }

                if(!visited.contains(node) && !queue.contains(node)){
                    paths.add(path);
                    queue.add(node);
                }
            }
        }
        if(drawNode){
            paths.forEach(path -> draw.drawLine(path, Color.RED, 1));
            visited.forEach(node -> draw.drawNode(node, Color.RED, 1));
        }
        return visited;
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
