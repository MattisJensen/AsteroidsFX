package dk.sdu.mmmi.cbse.common.data;

/**
 * CustomColor: A class for custom colors
 */
public class CustomColor {
    private int red;
    private int green;
    private int blue;

    /**
     * Constructor for CustomColor
     * The values have to be between 0 and 255
     *
     * @param red   the red value of the color
     * @param green the green value of the color
     * @param blue  the blue value of the color
     */
    public CustomColor(int red, int green, int blue) throws IllegalArgumentException {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    public int getRed() {
        return this.red;
    }

    public void setRed(int red) throws IllegalArgumentException {
        if (red >= 0 && red <= 255) {
            this.red = red;
        } else {
            throw new IllegalArgumentException("Red value must be between 0 and 255");
        }
    }

    public int getGreen() {
        return this.green;
    }

    public void setGreen(int green) throws IllegalArgumentException {
        if (green >= 0 && green <= 255) {
            this.green = green;
        } else {
            throw new IllegalArgumentException("Green value must be between 0 and 255");
        }
    }

    public int getBlue() {
        return this.blue;
    }

    public void setBlue(int blue) throws IllegalArgumentException {
        if (blue >= 0 && blue <= 255) {
            this.blue = blue;
        } else {
            throw new IllegalArgumentException("Blue value must be between 0 and 255");
        }
    }
}
