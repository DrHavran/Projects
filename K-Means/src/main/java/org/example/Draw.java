package org.example;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.Models.Bounds;
import org.example.Models.Cluster;
import org.example.Models.Node;

import java.util.Random;

public class Draw {
    private final Pane root;
    private final Logic logic;
    private final Bounds bounds;

    public Draw() {
        this.root = new Pane();
        this.logic = new Logic();
        this.bounds = logic.getBounds();
        drawNodes();
        drawClusters();
    }

    private void drawNodes() {
        Random rand = new Random();

        for(Cluster cluster : logic.getClusters()) {
            double r = rand.nextDouble();
            double g = rand.nextDouble();
            double b = rand.nextDouble();

            for(Node node : cluster.getNodes()){
                double newX = transformX(node.getX());
                double newY = transformY(node.getY());

                Circle dot = new Circle(newX, newY,2);
                dot.setFill(Color.color(r, g, b));
                root.getChildren().add(dot);
            }
        }
    }

    private void drawClusters() {
        for(Cluster cluster : logic.getClusters()) {
            double newX = transformX(cluster.getX());
            double newY = transformY(cluster.getY());
            Circle dot = new Circle(newX, newY,5);
            dot.setFill(Color.RED);
            root.getChildren().add(dot);
        }
    }

    private double transformX(double x) {
        return (x - bounds.minX()) / (bounds.maxX() - bounds.minX()) * Settings.screenWidth;
    }
    private double transformY(double y) {
        return Settings.screenHeight - (y - bounds.minY()) / (bounds.maxY() - bounds.minY()) * Settings.screenHeight;
    }

    public Pane getRoot() {
        return root;
    }
}
