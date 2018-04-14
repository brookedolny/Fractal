import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JuliaSetImage {
    private int iterations;
    private int order;
    private double resolution;
    private double scale;
    private double real;
    private double imaginary;
    private Julia julia;
    private BufferedImage img;
    private Graphics2D g2;
    private JuliaSetColouring colouring;

    public JuliaSetImage(double real, double imaginary, int resolution, int iterations, int order, double scale) {
        this.iterations = iterations;
        this.order = order;
        this.resolution = resolution;
        this.real = real;
        this.imaginary = imaginary;
        julia = new Julia(real, imaginary, this.order);
        this.scale = scale;
        img = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB);
        g2 = img.createGraphics();
        colouring = new JuliaSetColouring(new Julia(real, imaginary, this.order));
    }


    /**
     * Draws the MandelbrotSetImage to a {@code 2D Graphics} object.
     */
    public void drawImage() {
        for (int x = 0; x < resolution; x++) {
            for (int y = 0; y < resolution; y++) {
                g2.setColor(colouring.colourPixel((x - resolution / 2) / (resolution) * scale, (resolution / 2 - y) / (resolution) * scale, iterations));
                g2.drawRect(x, y, 1, 1);
            }
        }
    }

    /**
     * Saves the MandelbrotSetImage as a png file
     */
    public void saveImage() {
        try {
            if (ImageIO.write(img, "png", new File("./JuliaSetImage_"+real+"+"+imaginary+"i.png"))) {
                System.out.println("image saved");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
