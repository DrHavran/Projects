package org.example;

import org.example.Parts.Cluster;
import org.example.Parts.Node;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

public class Data {
    private final HashSet<Node> nodes;
    private final HashSet<Cluster> clusters;

    private double minX, minY, maxX, maxY;

    public Data() {
        this.nodes = new HashSet<>();
        this.clusters = new HashSet<>();
        loadData();
    }

    private void loadData() {
        try {
            Scanner sc = new Scanner(new File("Data.csv"));

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

            for (Node node : nodes) {
                node.set(
                        transformX(node.getX()),
                        transformY(node.getY())
                );
            }

            for(int i = 0; i < Settings.clusters; i++){
                double x = minX + Math.random() * (maxX - minX);
                double y = minY + Math.random() * (maxY - minY);

                clusters.add(new Cluster(transformX(x), transformY(y)));
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private double transformX(double x) {
        return (x - minX) / (maxX - minX) * Settings.screenWidth;
    }
    private double transformY(double y) {
        return Settings.screenHeight - (y - minY) / (maxY - minY) * Settings.screenHeight;
    }

    public HashSet<Node> getNodes() {
        return nodes;
    }
    public HashSet<Cluster> getClusters() {
        return clusters;
    }
}
