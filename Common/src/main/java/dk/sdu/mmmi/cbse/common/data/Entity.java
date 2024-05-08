package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Entity: A polygon based entity with a set of coordinates
 */
public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();

    private double[] polygonCoordinates;
    private CustomColor color;
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
    public Entity(CustomColor color, double... polygonCoordinates) {
        this.color = color;
        this.width = 0;
        this.height = 0;
        this.x = 0;
        this.y = 0;
        this.rotation = 0;
        this.polygonCoordinates = polygonCoordinates;

        setWidthByPolygon(polygonCoordinates);
        setHeightByPolygon(polygonCoordinates);
    }

    /**
     * Sets the width of the entity based on the provided polygon coordinates
     */
    public void setWidthByPolygon(double... coordinates) {
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
        for (int i = 0; i < this.polygonCoordinates.length; i += 2) {
            double x = this.polygonCoordinates[i];
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
        }
        this.width = maxX - minX;
    }

    /**
     * Sets the height of the entity based on the provided polygon coordinates
     */
    public void setHeightByPolygon(double... coordinates) {
        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        for (int i = 1; i < this.polygonCoordinates.length; i += 2) {
            double y = this.polygonCoordinates[i];
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }
        this.height = maxY - minY;
    }

    public String getID() {
        return ID.toString();
    }

    public void setPolygonCoordinates(double... coordinates) {
        this.polygonCoordinates = coordinates;
    }

    public double[] getPolygonCoordinates() {
        return this.polygonCoordinates;
    }

    public CustomColor getColor() {
        return this.color;
    }

    public void setColor(CustomColor color) {
        this.color = color;
    }

    public void setXCoordinate(double x) {
        this.x = x;
    }

    public double getXCoordinate() {
        return this.x;
    }

    public void setYCoordinate(double y) {
        this.y = y;
    }

    public double getYCoordinate() {
        return this.y;
    }

    public double getCenterXCoordinate() {
        return this.x + (this.width / 2);
    }

    public double getCenterYCoordinate() {
        return this.y + (this.height / 2);
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return this.rotation;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }
}
