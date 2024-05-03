package dk.sdu.mmmi.cbse.common.data;

/**
 * GameKeys: Handles the keys for the game
 */
public class GameKeys {
    private static boolean[] keys;
    private static boolean[] pkeys;

    private static final int NUM_KEYS = 4;
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int SPACE = 3;

    public GameKeys() {
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
    }

    /**
     * Updates the keys to the current state
     */
    public void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            pkeys[i] = keys[i];
        }
    }

    /**
     * Sets the key to the given state
     * @param k The key to set
     * @param b The state to set the key to
     */
    public void setKey(int k, boolean b) {
        keys[k] = b;
    }

    /**
     * Checks if the key is down
     * @param k The key to check
     * @return True if the key is down, false otherwise
     */
    public boolean isDown(int k) {
        return keys[k];
    }

    /**
     * Checks if the key is pressed
     * @param k The key to check
     * @return True if the key is pressed, false otherwise
     */
    public boolean isPressed(int k) {
        return keys[k] && !pkeys[k];
    }
}
