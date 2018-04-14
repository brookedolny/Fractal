import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MandelbrotSetImage {
    private int iterations;
    private int order;
    private final double resolution;
    private double scale;
    private BufferedImage img;
    private final Graphics2D g2;
    private MandelbrotSetColouring colouring;

    public MandelbrotSetImage(int resolution, int iterations, int order, double scale) {
        this.iterations = iterations;
        this.order = order;
        this.resolution = resolution;
        this.scale = scale;
        img = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB);
        g2 = img.createGraphics();
        colouring = new MandelbrotSetColouring(new Fractal(this.order));
    }


    /**
     * Draws the MandelbrotSetImage to a {@code 2D Graphics} object.
     */
    public void drawImage() {
        AtomicInteger jobCount = new AtomicInteger((int)resolution*(int)resolution);
        ExecutorService service = Executors.newFixedThreadPool(4);
        for (int x = 0; x < resolution; x++){
            for (int y = 0; y < resolution; y++) {
                final int x1 = x;
                final int y1 = y;
                service.submit( () -> {
                        Color c = colouring.colourPixel((x1 - resolution / 2) / (resolution) * scale, (resolution / 2 - y1) / (resolution) * scale, iterations);
                        synchronized(g2) {
                            g2.setColor(c);
                            g2.drawRect(x1, y1, 1, 1);
                        }
                        jobCount.decrementAndGet();
                   }
                );
                // g2.setColor(colouring.colourPixel((x - resolution / 2) / (resolution) * scale, (resolution / 2 - y) / (resolution) * scale, iterations));
                // g2.drawRect(x, y, 1, 1);
            }
            System.out.println("Report - Jobs remaining: " + jobCount.get());
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
            if (ImageIO.write(img, "png", new File("./MandelbrotSetImage.png"))) {
                System.out.println("image saved");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
