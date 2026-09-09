package org.example;

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
            step();
            for(Cluster cluster : data.getClusters()) {
                if (cluster.hasMoved()) {
                    movement = true;
                    break;
                }
            }
        }while (movement);
    }

    private void step() {
        for(Node node : data.getNodes()) {
            for(Cluster cluster : data.getClusters()) {
                double xDistance = Math.abs(cluster.getX() - node.getX());
                double yDistance = Math.abs(cluster.getY() - node.getY());
                double distance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

                if(distance < node.getClusterDistance()) {
                    Cluster currentCluster = node.getClosestCluster();
                    if(currentCluster != null) {
                        currentCluster.removeNode(node);
                    }

                    node.setClusterDistance(distance);
                    node.setClosestCluster(cluster);

                    cluster.addNode(node);
                }
            }
        }

        for(Cluster cluster : data.getClusters()) {
            cluster.average();
        }
    }

    public HashSet<Cluster> getClusters() {
        return data.getClusters();
    }
}