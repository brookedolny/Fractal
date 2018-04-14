import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JuliaSetImage {
    private int iterations;
    private double order;
    private double resolution;
    private double scale;
    private double real;
    private double imaginary;
    private Julia julia;
    private BufferedImage img;
    private Graphics2D g2;
    private JuliaSetColouring colouring;

    public JuliaSetImage(double real, double imaginary, int resolution, int iterations, double order, double scale) {
        this.iterations = iterations;
        this.order = order;
        this.resolution = resolution;
        this.real = real;
        this.imaginary = imaginary;
        julia = new Julia(new Complex(real, imaginary), this.order);
        this.scale = scale;
        img = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB);
        g2 = img.createGraphics();
        colouring = new JuliaSetColouring(julia);
    }


    /**
     * Draws the MandelbrotSetImage to a {@code 2D Graphics} object.
     */
    public void drawImage() {
        AtomicInteger jobCount = new AtomicInteger((int)resolution);
        ExecutorService service = Executors.newFixedThreadPool(4);
        for (int x = 0; x < resolution; x++) {
            final int x1 = x;
            service.submit(
                () -> {
                    for (int y = 0; y < resolution; y++) {
                        Color c = colouring.colourPixel((x1 - resolution / 2) / (resolution) * scale, (resolution / 2 - y) / (resolution) * scale, iterations);
                        synchronized (g2) {
                            g2.setColor(c);
                            g2.drawRect(x1, y, 1, 1);
                        }
                    }
                    jobCount.decrementAndGet();
                }
            );
        }
        service.shutdown();
        do {
            try {
                service.awaitTermination(20, TimeUnit.SECONDS);
                System.out.println("Jobs remaining: " + jobCount.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(!service.isTerminated());
    }

    /**
     * Saves the MandelbrotSetImage as a png file
     */
    public void saveImage() {
        try {
            if (ImageIO.write(img, "png", new File("./JuliaSetImage_"+real+"+"+imaginary+"i"+"order"+order+".png"))) {
                System.out.println("image saved");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
