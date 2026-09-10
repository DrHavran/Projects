package org.example;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.Draw.Draw;
import org.example.Models.*;
import org.example.PathFinder.*;

public class Logic {
    private Draw draw;
    private final Data data;

    public Logic() {
        this.data = new Data();
    }

    public void findPath(String startId, String endId){
        PathFinder pathFinder = getPathFinder(GlobalSettings.pathFindingAlgorithm);
        pathFinder.findPath(data.getNode(startId), data.getNode(endId));

        pathFinder.getNodes().forEach(node -> draw.drawNode(node, Color.RED));
        pathFinder.getPath().forEach(path -> draw.drawLine(path, Color.RED));
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
