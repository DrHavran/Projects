package org.example;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.Parts.Cluster;

import java.util.Random;

public class Draw {
    private final Pane root;
    private final Logic logic;

    public Draw() {
        this.root = new Pane();
        this.logic = new Logic();

        drawNodes();
        drawClusters();
    }

    private void drawNodes() {
        Random rand = new Random();

        for(Cluster cluster : logic.getClusters()) {
            double r = rand.nextDouble();
            double g = rand.nextDouble();
            double b = rand.nextDouble();

            cluster.getNodes().forEach(node -> {
                Circle dot = new Circle(node.getX(),node.getY(),2);
                dot.setFill(Color.color(r, g, b));
                root.getChildren().add(dot);
            });
        }
    }

    private void drawClusters() {
        for(Cluster cluster : logic.getClusters()) {
            Circle dot = new Circle(cluster.getX(), cluster.getY(),5);
            dot.setFill(Color.RED);
            root.getChildren().add(dot);
        }
    }

    public Pane getRoot() {
        return root;
    }
}
