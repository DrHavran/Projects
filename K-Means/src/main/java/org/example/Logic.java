package org.example;

import org.example.Models.Bounds;
import org.example.Models.Cluster;
import org.example.Models.Node;

import java.util.HashSet;

public class Logic {
    private final Data data;

    public Logic() {
        this.data = new Data();

        boolean movement;

        do{
            movement = false;

            for(Node node : data.getNodes()) {
                for(Cluster cluster : data.getClusters()) {
                    calculateNode(cluster, node);
                }
            }

            // Move the clusters based on the new data
            for(Cluster cluster : data.getClusters()) {
                if(cluster.step()){
                    movement = true;
                }
            }
        }while(movement);
    }

    private void calculateNode(Cluster cluster, Node node){
        double distance = calculateDistance(cluster, node);

        if(distance < node.getClusterDistance()) {
            Cluster currentCluster = node.getClosestCluster();
            if(currentCluster != null) {
                currentCluster.removeNode(node);
            }
            cluster.addNode(node);

            node.setClusterDistance(distance);
            node.setClosestCluster(cluster);
        }
    }

    private double calculateDistance(Cluster cluster, Node node) {
        double dx = cluster.getX() - node.getX();
        double dy = cluster.getY() - node.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Bounds getBounds(){
        return data.getBounds();
    }
    public HashSet<Cluster> getClusters() {
        return data.getClusters();
    }
}