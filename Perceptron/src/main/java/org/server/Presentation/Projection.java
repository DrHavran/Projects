package org.server.Presentation;

import org.server.GlobalSettings;
import org.server.Models.Entry;

import java.util.ArrayList;

public class Projection {

    private double minX, maxX, minY, maxY;
    private final double scale;
    private final double offX, offY;

    public Projection(ArrayList<Entry> entries) {
        fillMinMax(entries);

        double xRange = maxX - minX;
        double yRange = maxY - minY;

        if (xRange <= 0) xRange = 1e-9;
        if (yRange <= 0) yRange = 1e-9;

        this.scale = Math.min(
                GlobalSettings.screenWidth  / xRange,
                GlobalSettings.screenHeight / yRange
        );

        double drawnW = xRange * scale;
        double drawnH = yRange * scale;
        this.offX = (GlobalSettings.screenWidth  - drawnW) / 2.0;
        this.offY = (GlobalSettings.screenHeight - drawnH) / 2.0;
    }

    private void fillMinMax(ArrayList<Entry> entries) {
        minX =  Double.MAX_VALUE;
        maxX = -Double.MAX_VALUE;
        minY =  Double.MAX_VALUE;
        maxY = -Double.MAX_VALUE;

        for (Entry e : entries) {
            if (e.x() < minX) minX = e.x();
            if (e.x() > maxX) maxX = e.x();
            if (e.y() < minY) minY = e.y();
            if (e.y() > maxY) maxY = e.y();
        }
    }

    public double getMinY() {
        return minY;
    }
    public double getMinX() {
        return minX;
    }
    public double getMaxX() {
        return maxX;
    }
    public double getMaxY() {
        return maxY;
    }

    public double x(double x) {
        return offX + (x - minX) * scale;
    }
    public double y(double y) {
        return offY + (maxY - y) * scale;
    }
}