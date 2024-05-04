package dk.sdu.mmmi.cbse.common.data;

/**
 * GameData: Stores data about the game
 */
public class GameData {
    private int displayWidth  = 800 ;
    private int displayHeight = 800;
    private final GameKeys keys = new GameKeys();
    private double deltaTime;

    public GameKeys getKeys() {
        return this.keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return this.displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return this.displayHeight;
    }

    public double getDeltaTime() {
        return this.deltaTime;
    }

    public void setDeltaTime(double deltaTime) {
        this.deltaTime = deltaTime;
    }
}
