package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Entity: A polygon based entity with a set of coordinates
 */
public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();

    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;
    private double width;
    private double height;

    /**
     * Constructor for Entity
     *
     * @param polygonCoordinates The polygon coordinates
     */
    public Entity(double... polygonCoordinates) {
        setPolygonCoordinates(polygonCoordinates);
        this.width = 0;
        this.height = 0;
        this.x = 0;
        this.y = 0;
        this.rotation = 0;
    }

    public String getID() {
        return ID.toString();
    }

    /**
     * Sets the polygon coordinates of the entity
     * Sets the width and height of the entity based on the provided polygon coordinates
     *
     * @param coordinates The polygon coordinates
     */
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

    public void setXCoordinate(double x) {
        this.x = x;
    }

    public double getXCoordinate() {
        return x;
    }

    public void setYCoordinate(double y) {
        this.y = y;
    }

    public double getYCoordinate() {
        return y;
    }

    public double getCenterXCoordinate() {
        return x + (width / 2);
    }

    public double getCenterYCoordinate() {
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
}
