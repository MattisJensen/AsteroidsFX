package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();

    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;
    private double width;
    private double height;

    private double livePoints;
    private double damagePoints;
    private double movingSpeed;

    public Entity() {
        this.width = 0;
        this.height = 0;
        this.x = 0;
        this.y = 0;
        this.rotation = 0;
        this.livePoints = 1;
        this.damagePoints = 1;
        this.movingSpeed = 0;
    }

    public String getID() {
        return ID.toString();
    }

    public void setPolygonCoordinates(double... coordinates) {
        this.polygonCoordinates = coordinates;

        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
        for (int i = 0; i < polygonCoordinates.length; i += 2) {
            double x = polygonCoordinates[i];
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
        }
        this.width = maxX - minX;

        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        for (int i = 1; i < polygonCoordinates.length; i += 2) {
            double y = polygonCoordinates[i];
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }
        this.height = maxY - minY;
    }

    public double[] getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public double getCenterX() {
        return x + (width / 2);
    }

    public double getCenterY() {
        return y + (height / 2);
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getLivePoints() {
        return livePoints;
    }

    public void setLivePoints(double livePoints) {
        this.livePoints = livePoints;
    }

    public void removeLivePoints(double livePoints) {
        this.livePoints -= livePoints;
    }

    public double getMovingSpeed() {
        return movingSpeed;
    }

    public void setMovingSpeed(double movingSpeed) {
        this.movingSpeed = movingSpeed;
    }

    public double getCurrentDamage() {
        return damagePoints + movingSpeed * 1.2;
    }

    public double getDamagePoints() {
        return damagePoints;
    }

    public void setDamagePoints(double damagePoints) {
        this.damagePoints = damagePoints;
    }
}
