package org.server;

import javafx.scene.layout.Pane;
import org.server.Models.Shape;
import org.server.Presentation.Draw;
import org.server.Presentation.Projection;

public class Logic {
    private final Draw draw;
    private final Data data;
    private final Shape shape;

    public Logic(Shape shape) {
        this.data = new Data();
        this.draw = new Draw();
        this.shape = shape;
    }

    public void loadFile(String file){
        data.loadData(file, shape);
        draw.setProjection(new Projection(data.getEntries()));
    }

    public void runPerceptron(){
        Perceptron perceptron = new Perceptron();
        perceptron.run(data.getNodes());
        draw.drawPerceptron(perceptron, shape);
    }

    public void drawNodes(){
        data.getEntries().forEach(draw::drawPoint);
    }
    public Pane getRoot(){
        return draw.getRoot();
    }
    public Shape getShape() {
        return shape;
    }
}
