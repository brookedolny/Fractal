import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MandelbrotSetPanel extends JPanel {
    private int iterations;
    private final int MAX_ITERATIONS = 1000000; // increase this to get a clearer picture. For using the zoom/drag feature, I don't recommend going above 1000.
    private double renders = 1;
    private double scale = 4.0;
    private double x = 0;
    private double y = 0;
    private MandelbrotSetColouring colouring;

    // For scrolling in the GUI
    private class MSPMouseWheelListener implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getPreciseWheelRotation() > 0) {
                scale = scale * 0.99; // decreases scale when "zooming out"
            } else if (e.getPreciseWheelRotation() < 0) {
                scale = scale * 1.01; // increases scale when "zooming in"
            }
            iterations = 100000;
            MandelbrotSetPanel.this.invalidate();
            MandelbrotSetPanel.this.repaint();
        }

    }

    private class MSPMouseListener extends MouseAdapter {
        private boolean mousePressed = false;
        private int initialX;
        private int initialY;

        @Override
        public void mousePressed(MouseEvent e) {
            mousePressed = true;
            // gets the initial value for the dragging motion
            initialX = e.getX();
            initialY = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (mousePressed) {
                // change the x and y offset for the Mandelbrot Set
                x += e.getX() - initialX;
                y += e.getY() - initialY;
                iterations = 100;
                mousePressed = false;
                MandelbrotSetPanel.this.invalidate(); // to repaint the set
            }
        }

    }

    public MandelbrotSetPanel(int order) {
        super();
        colouring = new MandelbrotSetColouring(new Fractal(order));
        iterations = 100;
        addMouseWheelListener(new MSPMouseWheelListener());
        addMouseListener(new MSPMouseListener());
    }

    // this constructor is for an alternate project.
    public MandelbrotSetPanel(int order, int iterations) {
        super();
        colouring = new MandelbrotSetColouring(new Fractal(order));
        this.iterations = iterations;
    }

    /*
     * Worst-case time complexity of O((n*d)^2), where n is the number of iterations, and d is the order
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int w = 0; w < getWidth(); w++) {
            for(int h = 0; h < getHeight(); h++) {
                g.setColor(colouring.colourPixel((w - x - getWidth() / 2) / (getWidth() / scale), (getHeight() / 2 - h + y) / (getHeight() / scale), iterations));
                g.drawRect(w, h, 1, 1);
            }
        }

        if (iterations < MAX_ITERATIONS) {
            iterations *= 10;
            renders++;
            repaint();
        }
    }

}
