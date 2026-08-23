package org.example;

public record Path(Node start, Node end) {

    public double getLength() {
        double lon = start.getLongitude() - end.getLongitude();
        double lan = start.getLatitude() - end.getLatitude();
        return Math.sqrt(Math.pow(lon, 2) + Math.pow(lan, 2));
    }
}
