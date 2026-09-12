package org.server.Models;

import java.util.ArrayList;

public class Node {
    private final ArrayList<Double> values;
    private final double b;

    public Node(double x, double y, double b, Shape shape) {
        this.b = b;
        this.values = new ArrayList<>();

        values.add(x * b);
        values.add(y * b);

        //  Add additional features depending on the shape
        switch (shape) {
            case CIRCLE -> circle(x, y);
            case ELLIPSE -> ellipse(x, y);
            case HYPERBOLA -> hyperbola(x, y);
            case PARABOLA -> parabola(x);
            case CONIC -> conic(x, y);
        }

        values.add(b);
    }

    // [x, y, x² + y², 1]
    public void circle(double x, double y) {
        values.add((x * x + y * y) * b);
    }

    // [x, y, x², y², 1]
    public void ellipse(double x, double y) {
        values.add(x * x * b);
        values.add(y * y * b);
    }

    // [x, y, x², y², 1]  (same lift as ellipse)
    public void hyperbola(double x, double y) {
        values.add(x * x * b);
        values.add(y * y * b);
    }

    // [x, y, x², 1]
    public void parabola(double x) {
        values.add(x * x * b);
    }

    // [x, y, x², xy, y², 1]
    public void conic(double x, double y) {
        values.add(x * x * b);
        values.add(x * y * b);
        values.add(y * y * b);
    }

    public ArrayList<Double> getValues() {
        return values;
    }
}