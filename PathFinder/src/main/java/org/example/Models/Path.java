package org.example.Models;

public class Path {
    private final Node start, end;
    private double distance;

    public Path(Node start, Node end) {
        this.start = start;
        this.end = end;
        calculateDistance();
    }
    private void calculateDistance(){
        double lonDifference = start.getLongitude() - end.getLongitude();
        double lanDifference = start.getLatitude() - end.getLatitude();
        this.distance = Math.sqrt(Math.pow(lonDifference, 2) + Math.pow(lanDifference, 2));
    }

    public Node getStart() {
        return start;
    }
    public Node getEnd() {
        return end;
    }
    public double getDistance() {
        return distance;
    }
}