package org.example.Draw;

import org.example.GlobalSettings;
import org.example.Models.Node;

import java.util.Collection;

public class Projection {

    private double minLon, maxLon, minLat, maxLat;
    private final double cosLat, scale;
    private final double offX, offY;

    public Projection(Collection<Node> nodes) {
        fillMinMax(nodes);

        double midLat = Math.toRadians((minLat + maxLat) / 2.0);
        this.cosLat = Math.cos(midLat);

        double lonRange = (maxLon - minLon) * cosLat;
        double latRange = (maxLat - minLat);

        if (lonRange <= 0) lonRange = 1e-9;
        if (latRange <= 0) latRange = 1e-9;

        this.scale = Math.min(GlobalSettings.screenWidth / lonRange, GlobalSettings.screenHeight / latRange);

        double drawnW = lonRange * scale;
        double drawnH = latRange * scale;
        this.offX = (GlobalSettings.screenWidth  - drawnW) / 2.0;
        this.offY = (GlobalSettings.screenHeight - drawnH) / 2.0;
    }

    private void fillMinMax(Collection<Node> nodes){
        minLat =  Double.MAX_VALUE;
        maxLat = -Double.MAX_VALUE;
        minLon =  Double.MAX_VALUE;
        maxLon = -Double.MAX_VALUE;

        for (Node n : nodes) {
            if (n.getLatitude() < minLat) minLat = n.getLatitude();
            if (n.getLatitude() > maxLat) maxLat = n.getLatitude();
            if (n.getLongitude() < minLon) minLon = n.getLongitude();
            if (n.getLongitude() > maxLon) maxLon = n.getLongitude();
        }
    }

    public double x(double lon) {
        return offX + (lon - minLon) * cosLat * scale;
    }
    public double y(double lat) {
        return offY + (maxLat - lat) * scale;
    }
}