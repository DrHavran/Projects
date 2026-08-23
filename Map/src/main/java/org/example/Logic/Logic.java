package org.example.Logic;

import org.example.Data.Data;
import org.example.Logic.PathFinding.*;
import org.example.Node;
import org.example.Path;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class Logic {
    private final Data data;
    private PathFinder pathFinder;

    public Logic(String fileLink) {
        this.data = new Data(fileLink);
    }

    public ArrayList<Path> colorAllPaths(Long id) {
        ArrayList<Path> paths = new ArrayList<>();
        HashSet<Node> visited = new HashSet<>();
        HashSet<Node> unVisited = new HashSet<>();

        unVisited.add(getNode(id));
        while(!unVisited.isEmpty()) {
            Node next = unVisited.iterator().next();
            for (Node selected : next.getPaths()){
                if(!visited.contains(selected)){
                    unVisited.add(selected);
                    paths.add(new Path(selected, next));
                }
            }
            unVisited.remove(next);
            visited.add(next);
        }

        return paths;
    }

    public Node findCenter(long id){
        HashSet<Node> visited = new HashSet<>();
        HashSet<Node> unVisited = new HashSet<>();

        unVisited.add(getNode(id));
        while(!unVisited.isEmpty()){
            Node next = unVisited.iterator().next();
            for(Node selected : next.getPaths()){
                if(!visited.contains(selected)){
                    unVisited.add(selected);
                }
            }
            unVisited.remove(next);
            visited.add(next);
        }

        System.out.println("Number of nodes center: " + visited.size());
        Node center = null;
        double minValue = Double.MAX_VALUE;
        int count = 0;


        for(Node parent : visited){
            double length = 0;
            count++;
            System.out.println("Checking node " + count);
            for(Node child : visited){
                if(!child.equals(parent)){
                    pathFinder.findPath(parent, child);
                    length = length + pathFinder.getLength();
                }
            }
            if(minValue > length){
                minValue = length;
                center = parent;
            }
        }

        return center;
    }

    public ArrayList<Path> findPath(long start, long end) {
        pathFinder.findPath(getNode(start), getNode(end));
        return pathFinder.getFinalPath();
    }

    public void switchMethod(String method){
        switch (method) {
            case "DFS" -> this.pathFinder = new DFS();
            case "BFS" -> this.pathFinder = new BFS();
            case "DSA" -> this.pathFinder = new DSA();
            case "A*" -> this.pathFinder = new AStar();
            default -> System.out.println("Invalid method");
        }
    }

    public double scaleX(double number){
        return data.scaleX(number);
    }
    public double scaleY(double number){
        return data.scaleY(number);
    }

    public Hashtable<Long, Node> getNodes() {return data.getNodes();}
    public ArrayList<Path> getPaths() {
        return data.getPaths();
    }
    public Node getNode(long id) {
        return data.getNodes().get(id);
    }
    public String getScore(){
        String method = pathFinder.getClass().getSimpleName();
        return method + " took " + pathFinder.getSteps() + " steps";
    }
}