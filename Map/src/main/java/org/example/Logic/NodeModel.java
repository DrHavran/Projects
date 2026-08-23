package org.example.Logic;

public class NodeModel {

    private final double maxX;
    private final double minX;
    private final double maxY;
    private final double minY;

    public NodeModel(double maxX, double minX, double maxY, double minY) {
        this.maxX = maxX;
        this.minX = minX;
        this.maxY = maxY;
        this.minY = minY;
    }

    public double scaleX(double number){
        return (number - minX) / (maxX - minX) * Settings.screenWidth;
    }
    public double scaleY(double number){
        return Settings.screenHeight - (number-minY) / (maxY - minY) * Settings.screenHeight;
    }

}
