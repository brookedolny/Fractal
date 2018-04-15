import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MandelbrotSetImage {
    private int iterations;
    private double order;
    private double xoffset;
    private double yoffset;
    private final double xresolution;
    private final double yresolution;
    private double scale;
    private BufferedImage img;
    private final Graphics2D g2;
    private MandelbrotSetColouring colouring;

    public MandelbrotSetImage(int xresolution, int yresolution, int iterations, double order, double xoffset, double yoffset, double scale) {
        this.iterations = iterations;
        this.order = order;
        this.xresolution = xresolution;
        this.yresolution = yresolution;
        this.scale = scale;
        this.xoffset = xoffset;
        this.yoffset = yoffset;
        img = new BufferedImage(xresolution, yresolution, BufferedImage.TYPE_INT_RGB);
        g2 = img.createGraphics();
        colouring = new MandelbrotSetColouring(new Fractal(this.order));
    }


    /**
     * Draws the MandelbrotSetImage to a {@code 2D Graphics} object.
     */
    public void drawImage() {
        AtomicInteger jobCount = new AtomicInteger((int)xresolution);
        AtomicInteger minimum = new AtomicInteger(256);
        AtomicInteger maximum = new AtomicInteger(0);
        ExecutorService service = Executors.newFixedThreadPool(4);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, (int) xresolution, (int) yresolution);
        final double maxres = Math.max(xresolution, yresolution);
        for (int x = 0; x < xresolution; x++){
            final int x1 = x;
            service.submit(
                () -> {
                    for (int y = 0; y < yresolution; y++) {
                        double xcentre = (2 * x1 - xresolution) / maxres * scale;
                        double ycentre = (yresolution - 2 * y) / maxres * scale;
                        Color c = colouring.colourPixel(xcentre + xoffset, ycentre + yoffset, iterations);

                        int value_seen = minimum.get();
                        while(value_seen > c.getAlpha()) {
                            if(minimum.weakCompareAndSet(value_seen, c.getAlpha())) {
                                break;
                            }
                            value_seen = minimum.get();
                        }

                        value_seen = maximum.get();
                        while(value_seen < c.getAlpha()) {
                            if(maximum.weakCompareAndSet(value_seen, c.getAlpha())){
                                break;
                            }
                            value_seen = maximum.get();
                        }

                        if(c.equals(Color.BLACK)) {
                            continue;
                        }

                        c = new Color(c.getRed(), c.getGreen(), c.getBlue());

                        synchronized(g2) {
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
                service.awaitTermination(30, TimeUnit.SECONDS);
                System.out.println("Jobs remaining: " + jobCount.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(!service.isTerminated());
        System.out.println(minimum.get());
        System.out.println(maximum.get());
    }

    /**
     * Saves the MandelbrotSetImage as a png file
     */
    public void saveImage(String folder) {
        try {
            if (ImageIO.write(img, "png", new File(folder+"/MandelbrotSetImage" + Math.round(order*100) + ".png"))) {
                System.out.println("image saved");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
