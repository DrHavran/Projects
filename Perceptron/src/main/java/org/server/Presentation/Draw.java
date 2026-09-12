package org.server.Presentation;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import org.server.Models.Entry;
import org.server.Models.Shape;
import org.server.Perceptron;

import java.util.List;

public class Draw {
    private final Pane root;
    private Projection projection;
    private static final int STEPS = 15000;
    private static final double EPS = 1e-9;

    public Draw() {
        this.root = new Pane();
    }

    /**
     *  Draw is made by AI, because its just math to "detransition" perceptron into 2D space
     */

    public void drawPerceptron(Perceptron perceptron, Shape shape) {
        List<Double> w = perceptron.getWeights();
        switch (shape) {
            case LINEAR    -> drawLinear(w);
            case CIRCLE    -> drawCircle(w);
            case ELLIPSE   -> drawEllipse(w);
            case HYPERBOLA -> drawHyperbola(w);
            case PARABOLA  -> drawParabola(w);
            case CONIC     -> drawConic(w);
        }
    }

    // w1*x + w2*y + b = 0
    private void drawLinear(List<Double> w) {
        double w1 = w.get(0), w2 = w.get(1), b = w.get(2);

        double x1, y1, x2, y2;
        if (Math.abs(w2) > EPS) {
            x1 = projection.getMinX();
            y1 = -(w1 * x1 + b) / w2;
            x2 = projection.getMaxX();
            y2 = -(w1 * x2 + b) / w2;
        } else {
            double xv = -b / w1;
            x1 = xv; y1 = projection.getMinY();
            x2 = xv; y2 = projection.getMaxY();
        }
        Line line = new Line(projection.x(x1), projection.y(y1),
                projection.x(x2), projection.y(y2));
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        root.getChildren().add(line);
    }

    // w1*x + w2*y + w3*(x² + y²) + b = 0
    private void drawCircle(List<Double> w) {
        double w1 = w.get(0), w2 = w.get(1), w3 = w.get(2), b = w.get(3);

        double cx = -w1 / (2 * w3);
        double cy = -w2 / (2 * w3);
        double r  = Math.sqrt(cx * cx + cy * cy - b / w3);

        Polyline pl = newLine();
        for (int i = 0; i <= STEPS; i++) {
            double t = 2 * Math.PI * i / STEPS;
            pl.getPoints().addAll(
                    projection.x(cx + r * Math.cos(t)),
                    projection.y(cy + r * Math.sin(t))
            );
        }
    }

    // w1*x + w2*y + w3*x² + w4*y² + b = 0
    private void drawEllipse(List<Double> w) {
        double w1 = w.get(0), w2 = w.get(1), w3 = w.get(2), w4 = w.get(3), b = w.get(4);

        double cx = -w1 / (2 * w3);
        double cy = -w2 / (2 * w4);
        double R  = w1 * w1 / (4 * w3) + w2 * w2 / (4 * w4) - b;

        double a  = Math.sqrt(R / w3);
        double bb = Math.sqrt(R / w4);

        Polyline pl = newLine();
        for (int i = 0; i <= STEPS; i++) {
            double t = 2 * Math.PI * i / STEPS;
            pl.getPoints().addAll(
                    projection.x(cx + a  * Math.cos(t)),
                    projection.y(cy + bb * Math.sin(t))
            );
        }
    }

    // w1*x + w2*y + w3*x² + w4*y² + b = 0
    private void drawHyperbola(List<Double> w) {
        double w1 = w.get(0), w2 = w.get(1), w3 = w.get(2), w4 = w.get(3), b = w.get(4);

        if (Math.abs(w3) < EPS) {
            System.out.println("drawHyperbola: w3 ≈ 0, nothing to draw");
            return;
        }

        // Degenerate: no y² term → boundary collapses to vertical line(s)
        if (Math.abs(w4) < EPS) {
            double disc = w1 * w1 - 4 * w3 * b;
            if (disc < 0) {
                System.out.println("drawHyperbola: degenerate, no real roots");
                return;
            }
            double s = Math.sqrt(disc);
            root.getChildren().addAll(
                    vertical((-w1 + s) / (2 * w3)),
                    vertical((-w1 - s) / (2 * w3))
            );
            return;
        }

        double cx = -w1 / (2 * w3);
        double cy = -w2 / (2 * w4);
        double R  = w1 * w1 / (4 * w3) + w2 * w2 / (4 * w4) - b;

        boolean xMajor = R / w3 > 0;
        double aArg  = xMajor ?  R / w3 : -R / w3;
        double bbArg = xMajor ? -R / w4 :  R / w4;

        if (aArg <= 0 || bbArg <= 0) {
            System.out.println("drawHyperbola: no real hyperbola");
            return;
        }

        double a  = Math.sqrt(aArg);
        double bb = Math.sqrt(bbArg);

        double tMax = 3.0;
        for (int sign = -1; sign <= 1; sign += 2) {
            Polyline pl = newLine();
            for (int i = 0; i <= STEPS; i++) {
                double t = -tMax + 2 * tMax * i / STEPS;
                double px, py;
                if (xMajor) {
                    px = cx + sign * a * Math.cosh(t);
                    py = cy + bb * Math.sinh(t);
                } else {
                    px = cx + a * Math.sinh(t);
                    py = cy + sign * bb * Math.cosh(t);
                }
                pl.getPoints().addAll(projection.x(px), projection.y(py));
            }
        }
    }

    // w1*x + w2*y + w3*x² + b = 0
    private void drawParabola(List<Double> w) {
        double w1 = w.get(0), w2 = w.get(1), w3 = w.get(2), b = w.get(3);

        Polyline pl = newLine();
        double xMin = projection.getMinX(), xMax = projection.getMaxX();

        if (Math.abs(w2) > EPS) {
            for (int i = 0; i <= STEPS; i++) {
                double x = xMin + (xMax - xMin) * i / STEPS;
                double y = -(w3 * x * x + w1 * x + b) / w2;
                pl.getPoints().addAll(projection.x(x), projection.y(y));
            }
        } else {
            // degenerate: vertical lines at the roots
            double disc = w1 * w1 - 4 * w3 * b;
            if (disc >= 0) {
                double x1 = (-w1 + Math.sqrt(disc)) / (2 * w3);
                double x2 = (-w1 - Math.sqrt(disc)) / (2 * w3);
                root.getChildren().addAll(
                        vertical(x1),
                        vertical(x2)
                );
            }
        }
    }

    // w1*x + w2*y + w3*x² + w4*xy + w5*y² + b = 0
    private void drawConic(List<Double> w) {
        double w1 = w.get(0), w2 = w.get(1), w3 = w.get(2),
                w4 = w.get(3), w5 = w.get(4), b = w.get(5);

        double xMin = projection.getMinX(), xMax = projection.getMaxX();
        int N = STEPS;

        double[]  xs   = new double[N + 1];
        Double[]  yUp  = new Double[N + 1];
        Double[]  yDn  = new Double[N + 1];

        // Sample: for each x, solve w5·y² + (w2 + w4·x)·y + (w3·x² + w1·x + b) = 0
        for (int i = 0; i <= N; i++) {
            double x = xMin + (xMax - xMin) * i / N;
            xs[i] = x;
            double A = w5;
            double B = w2 + w4 * x;
            double C = w3 * x * x + w1 * x + b;
            if (Math.abs(A) > EPS) {
                double disc = B * B - 4 * A * C;
                if (disc >= 0) {
                    double s = Math.sqrt(disc);
                    yUp[i] = (-B + s) / (2 * A);
                    yDn[i] = (-B - s) / (2 * A);
                }
            } else if (Math.abs(B) > EPS) {
                yUp[i] = -C / B;
                yDn[i] = yUp[i];
            }
        }

        // Walk the x-range, splitting into contiguous segments (separated by gaps)
        int i = 0;
        while (i <= N) {
            while (i <= N && yUp[i] == null) i++;
            if (i > N) break;
            int start = i;
            while (i <= N && yUp[i] != null) i++;
            int end = i - 1;

            // Ellipse: roots meet at both ends. Hyperbola: only at one (the vertex).
            int mid = (start + end) / 2;
            double midGap   = Math.abs(yUp[mid] - yDn[mid]);
            double gapStart = Math.abs(yUp[start] - yDn[start]);
            double gapEnd   = Math.abs(yUp[end]   - yDn[end]);
            boolean closed = midGap > EPS
                    && gapStart < midGap * 0.05
                    && gapEnd   < midGap * 0.05;

            if (closed) {
                // one closed loop: upper left→right, then lower right→left
                Polyline pl = newLine();
                for (int k = start; k <= end; k++) {
                    pl.getPoints().addAll(projection.x(xs[k]), projection.y(yUp[k]));
                }
                for (int k = end; k >= start; k--) {
                    pl.getPoints().addAll(projection.x(xs[k]), projection.y(yDn[k]));
                }
            } else {
                // two open branches
                Polyline top = newLine();
                Polyline bot = newLine();
                for (int k = start; k <= end; k++) {
                    top.getPoints().addAll(projection.x(xs[k]), projection.y(yUp[k]));
                    bot.getPoints().addAll(projection.x(xs[k]), projection.y(yDn[k]));
                }
            }
        }
    }

    private Polyline newLine() {
        Polyline pl = new Polyline();
        pl.setStroke(Color.BLACK);
        pl.setStrokeWidth(2);
        pl.setFill(null);
        root.getChildren().add(pl);
        return pl;
    }

    private Line vertical(double x) {
        Line l = new Line(projection.x(x), projection.y(projection.getMinY()),
                projection.x(x), projection.y(projection.getMaxY()));
        l.setStroke(Color.BLACK);
        l.setStrokeWidth(2);
        return l;
    }

    public void drawPoint(Entry entry) {
        double x = projection.x(entry.x());
        double y = projection.y(entry.y());

        Circle dot = new Circle(x, y, 2);
        dot.setFill(entry.classification() == 0 ? Color.BLUE : Color.RED);
        root.getChildren().add(dot);
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public Pane getRoot() {
        return root;
    }
}