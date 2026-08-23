package org.example.Presentation;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.Logic.Logic;
import org.example.Node;
import org.example.Path;
import org.example.Logic.Settings;

public class Draw {
    private final Pane root;
    private final Logic logic;

    public Draw() {
        this.root = new Pane();
        this.logic = new Logic("Intersection");

        drawMap();

        logic.switchMethod("A*");
        //findPath(66973468L, 693323283L); //Street
        //drawAllConnections(66973468L);
        //findCenter(66973468L);

        //drawAllConnections(11914214043L); //House
        //findCenter(11914214043L);

        //drawAllConnections(25376608L); //Intersection
        findCenter(25376608L);

        drawScore();
    }

    private void findCenter(long id){
        drawDot(logic.findCenter(id), Color.RED);
    }
    private void findPath(long start, long end){
        drawDot(logic.getNode(start), Color.GREEN);
        drawDot(logic.getNode(end), Color.BLUE);

        for(Path path : logic.findPath(start, end)){
            drawLine(path, Color.RED);
        }
    }

    private void drawMap(){
        for(Node node : logic.getNodes().values()) {
            drawNode(node);
        }
        for(Path path : logic.getPaths()) {
            drawLine(path, Color.BLACK);
        }
    }
    private void drawAllConnections(long id){
        for(Path path : logic.colorAllPaths(id)) {
            drawLine(path, Color.RED);
        }
    }
    private void drawScore(){
        Text text = new Text(10, 20, logic.getScore());
        text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        text.setFill(Color.RED);

        text.setStroke(Color.BLACK);
        text.setStrokeWidth(1);

        root.getChildren().add(text);
    }

    private void drawLine(Path path, Paint color) {
        double startX = logic.scaleX(path.start().getLongitude());
        double startY = logic.scaleY(path.start().getLatitude());
        double endX = logic.scaleX(path.end().getLongitude());
        double endY = logic.scaleY(path.end().getLatitude());

        Line line = new Line(startX, startY, endX, endY);

        line.setStrokeWidth(Settings.dotSize);
        line.setStroke(color);
        root.getChildren().add(line);
    }
    private void drawNode(Node node) {
        Circle dot = new Circle(logic.scaleX(node.getLongitude()), logic.scaleY(node.getLatitude()), Settings.dotSize);
        root.getChildren().add(dot);
    }
    private void drawDot(Node node, Paint color) {
        Circle dot = new Circle(logic.scaleX(node.getLongitude()), logic.scaleY(node.getLatitude()), Settings.dotSize+3);
        dot.setFill(color);
        root.getChildren().add(dot);
    }

    public Pane getRoot() {
        return root;
    }
}
