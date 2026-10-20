package org.example;

import org.example.Models.Bounds;
import org.example.Models.Cluster;
import org.example.Models.Node;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

public class Data {
    private final HashSet<Node> nodes;
    private final HashSet<Cluster> clusters;
    private Bounds bounds;

    public Data() {
        this.nodes = new HashSet<>();
        this.clusters = new HashSet<>();
        loadData();
    }

    private void loadData() {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        try {
            Scanner sc = new Scanner(new File("data.csv"));

            // Load nodes while also keeping track of MIN/MAX of Y and X
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                double x = Double.parseDouble(line[0]);
                if(x < minX) minX = x;
                if(x > maxX) maxX = x;

                double y = Double.parseDouble(line[1]);
                if(y < minY) minY = y;
                if(y > maxY) maxY = y;

                nodes.add(new Node(x, y));
            }
            sc.close();

            for(int i = 0; i < Settings.clusters; i++){
                double x = minX + Math.random() * (maxX - minX);
                double y = minY + Math.random() * (maxY - minY);

                clusters.add(new Cluster(x, y));
            }

            this.bounds = new Bounds(minX, maxX, minY, maxY);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Bounds getBounds() {
        return bounds;
    }
    public HashSet<Node> getNodes() {
        return nodes;
    }
    public HashSet<Cluster> getClusters() {
        return clusters;
    }
}
