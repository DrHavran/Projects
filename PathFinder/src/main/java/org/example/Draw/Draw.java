package org.example.Draw;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.example.Data;
import org.example.GlobalSettings;
import org.example.Models.Node;
import org.example.Models.Path;

import java.util.ArrayList;

public class Draw {
    private final Pane root;
    private final Data data;
    private final Projection projection;

    public Draw(Data providedData) {
        this.root = new Pane();
        this.data = providedData;
        this.projection = new Projection(new ArrayList<>(this.data.getNodes().values()));
    }

    public void drawMap(){
        for (Path path : data.getPaths()) {
            drawLine(path, Color.BLACK, 1);
        }
        for (Node node : data.getNodes().values()) {
            drawNode(node, Color.BLACK, 1);
        }
    }

    public void drawLine(Path path, Paint color, int size) {
        double startX = projection.x(path.getStart().getLongitude());
        double startY = projection.y(path.getStart().getLatitude());
        double endX   = projection.x(path.getEnd().getLongitude());
        double endY   = projection.y(path.getEnd().getLatitude());

        Line line = new Line(startX, startY, endX, endY);
        line.setStrokeWidth(size);
        line.setStroke(color);
        root.getChildren().add(line);
    }

    public void drawNode(Node node, Paint color, int size) {
        double x = projection.x(node.getLongitude());
        double y = projection.y(node.getLatitude());

        Circle dot = new Circle(x, y, size);
        dot.setFill(color);
        root.getChildren().add(dot);
    }

    public Pane getRoot() {
        return root;
    }
}