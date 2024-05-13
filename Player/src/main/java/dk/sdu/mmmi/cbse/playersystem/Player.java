package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.CustomColor;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.entityproperties.ICollidable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IDestroyable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IMoveable;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Player: A player entity which can be controlled by the user
 */
public class Player extends Entity implements ICollidable, IMoveable, IDestroyable {
    private double movingSpeed;
    private double livePoints;
    private double lastShotTime;
    private final URL scoreAddURL;
    private final URL scoreRemoveURL;
    private final HttpClient httpClient;


    /**
     * Constructor for the Player
     *
     * @param movingSpeed      the moving speed of the player
     * @param livePoints       the live points of the player
     * @param color            the color of the player
     * @param scoreAddURL      the URL with the endpoint to add the score with a PUT request.
     * @param shapeCoordinates the shape coordinates of the player
     */
    Player(double movingSpeed, double livePoints, CustomColor color, URL scoreAddURL, URL scoreRemoveURL, double... shapeCoordinates) {
        super(color, shapeCoordinates);
        this.movingSpeed = movingSpeed;
        this.livePoints = livePoints;
        this.lastShotTime = 0;
        this.scoreAddURL = scoreAddURL;
        this.scoreRemoveURL = scoreRemoveURL;
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Determine if the entity is allowed to shoot, depending on the entity's weapon cooldown
     * Set the last time the entity shot if the entity is allowed to shoot
     *
     * @param cooldown            the interval in milliseconds that the entity needs to wait before it can shoot again
     * @param currentTimeInMillis the current time in milliseconds
     * @return true if the entity is allowed to shoot, false otherwise
     */
    public boolean isAllowedToShoot(double cooldown, double currentTimeInMillis) {
        if (cooldown < currentTimeInMillis - this.lastShotTime) {
            this.lastShotTime = currentTimeInMillis;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void collision(World world, Entity entity) {
        if (entity instanceof IDestroyable destroyable) {
            destroyable.removeLivePoints(this.livePoints);

            if (destroyable.getLivePoints() <= 0) {
                collisionRemoval(world, entity);
            }
        }
    }

    @Override
    public void collisionRemoval(World world, Entity entity) {
        if (entity instanceof Bullet) {
            removeScore(this.scoreRemoveURL, 1);
        } else if (entity instanceof IMoveable) {
            addScore(this.scoreAddURL, 25);
        } else {
            addScore(this.scoreAddURL, 5);
        }
        world.removeEntity(entity);
    }

    /**
     * Add points to the score
     *
     * @param scoreAddURL the URL with the endpoint to add the score with a PUT request.
     * @param pointsToAdd the points to add to the score
     */
    private void addScore(URL scoreAddURL, long pointsToAdd) {
        URI scoreURI = URI.create(scoreAddURL.toString() + pointsToAdd);

        HttpRequest requestAddToScore = HttpRequest.newBuilder()
                .uri(scoreURI)
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build();

        // Send the request
        try {
            this.httpClient.send(requestAddToScore, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to add points to score");
        }
    }

    /**
     * Remove points from the score
     *
     * @param scoreRemoveURL the URL with the endpoint to remove the score with a PUT request.
     * @param pointsToRemove the points to remove from the score
     */
    private void removeScore(URL scoreRemoveURL, long pointsToRemove) {
        URI scoreURI = URI.create(scoreRemoveURL.toString() + pointsToRemove);

        HttpRequest requestRemoveFromScore = HttpRequest.newBuilder()
                .uri(scoreURI)
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build();

        // Send the request
        try {
            this.httpClient.send(requestRemoveFromScore, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to remove points from score");
        }
    }

    @Override
    public double getMovingSpeed() {
        return this.movingSpeed;
    }

    @Override
    public void setMovingSpeed(double movingSpeed) {
        this.movingSpeed = movingSpeed;
    }

    @Override
    public void removeLivePoints(double damagePoints) {
        this.livePoints -= damagePoints;
    }

    @Override
    public double getLivePoints() {
        return this.livePoints;
    }

    @Override
    public void setLivePoints(double livePoints) {
        this.livePoints = livePoints;
    }

    public void setLastShotTime(double lastShotTime) {
        this.lastShotTime = lastShotTime;
    }

    public double getLastShotTime() {
        return this.lastShotTime;
    }
}
