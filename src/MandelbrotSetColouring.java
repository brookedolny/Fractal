import java.awt.*;

public class MandelbrotSetColouring {
    /**
     * The {@code Fractal} object to be drawn to the {@code BufferedImage}
     */
    private Fractal fractal;
    /**
     * An array of {@code Color} objects
     */
    private Color[] colours = {
          new Color(0x2c3e50)
        , new Color(0xecf0f1)
        , new Color(0xe74c3c)
        , new Color(0x8e44ad)
        , new Color(0x3498db)
        , new Color(0xecf0f1)
        , new Color(0x2c3e50)
    };

//    private Color[] colours = {
//            new Color(255, 0, 0), new Color(255, 32, 0),
//            new Color(255, 64, 0), new Color(255, 83, 0),
//            new Color(255, 102, 0), new Color(255, 127, 0),
//            new Color(255, 152, 0), new Color(255, 176, 0),
//            new Color(255, 200, 0), new Color(255, 221, 0),
//            new Color(255, 242, 0), new Color(227, 249, 0),
//            new Color(199, 255, 0), new Color(163, 255, 0),
//            new Color(127, 255, 0), new Color(127, 255, 0),
//            new Color(0, 255, 0), new Color(0, 255, 50),
//            new Color(0, 255, 100), new Color(0, 255, 150),
//            new Color(0, 255, 200), new Color(0, 255, 221),
//            new Color(0, 255, 242), new Color(0, 223, 249),
//            new Color(0, 190, 255), new Color(0, 155, 255),
//            new Color(0, 120, 255), new Color(0, 60, 255),
//            new Color(0, 0, 255), new Color(70, 0, 255),
//            new Color(140, 0, 255), new Color(198, 0, 255),
//            new Color(255, 0, 255), new Color(255, 0, 205),
//            new Color(255, 0, 155), new Color(255, 0, 103),
//            new Color(255, 0, 50), new Color(255, 0, 25),
//            new Color(255, 0, 0)};

    private int[] coloursBW = new int[255];

    /**
     * Constructs a the MandelbrotSetColouring Object given a fractal object
     * @param fractal
     */
    public MandelbrotSetColouring(Fractal fractal) {
        this.fractal = fractal;
    }

    /**
     * Use a ratio to determine what colour to colour each pixel.
     * Uses a linear interpolation between two colours in the array of colours
     * to determine the colour of each pixel.
     * <p>
     * This algorithm runs with a time complexity of O(1).
     *
     * @param value the value that will determine the colour
     * @param ratio what ratio of each colour to use to blend two colours
     * @return the blended colour
     */
    private Color linearInterpolateColor(double value, double ratio) {
        //TODO fix this to make it look smoother?
        // mu = 1 + n + math.log2(math.log2(z))  / math.log2(2)
        int index1 = Math.min(colours.length - 1, (int) value);
        int index2 = Math.min(colours.length - 1, (int) value + 1);

        int red = (int) (colours[index2].getRed() * ratio + colours[index1].getRed() * (1 - ratio));
        int green = (int) (colours[index2].getGreen() * ratio + colours[index1].getGreen() * (1 - ratio));
        int blue = (int) (colours[index2].getBlue() * ratio + colours[index1].getBlue() * (1 - ratio));
        return new Color(red, green, blue);
    }

    private Color nonLinearInterpolationColor(double value) {
        return colours[(int) value % colours.length];
    }

    /**
     * Colour a pixel, using it's location in the frame as it's theoretical position in the complex plane.
     * <p>
     * This method has a worst-case time complexity of O(n*d),
     * where n is the number of iterations, and d is the order
     *
     * @param real the real part of the complex number
     * @param imaginary the imaginary part of the complex number
     * @return the colour that a pixel will be coloured.
     */
    public Color colourPixel(double real, double imaginary, int iterations) {
        // TODO try to make this colour transition smoother
        double ratio = fractal.iterate(new Complex(real, imaginary), iterations);
        ratio = colours.length * ratio / (1.0 + iterations);
        if (ratio == 0) {
            return new Color(0,0,0);
        }

        Color returnedColour = linearInterpolateColor(ratio, ratio % 1);
        return new Color(returnedColour.getRed(), returnedColour.getGreen(), returnedColour.getBlue(), (int)(255 * ratio / colours.length));
    }

}
