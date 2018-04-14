import javax.swing.*;
import java.awt.*;

public class JuliaSetPanel extends JPanel{
    private JuliaSetColouring colouring;
    private int iterations;
    private final int MAX_ITERATIONS = 100000;
    private double scale = 4.0;
    private double real;
    private double imaginary;
    private int renders = 0;

    public JuliaSetPanel(double real, double imaginary, int order, int iterations) {
        super();
        colouring = new JuliaSetColouring(new Julia(real, imaginary, order));
        this.iterations = iterations;
    }

    public JuliaSetPanel(double real, double imaginary, int order) {
        super();
        colouring = new JuliaSetColouring(new Julia(real, imaginary, order));
        this.iterations = MAX_ITERATIONS;
    }

    /*
     * Worst-case time complexity of O((n*d)^2), where n is the number of iterations, and d is the order
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int w = 0; w < getWidth(); w++) {
            for(int h = 0; h < getHeight(); h++) {
                g.setColor(colouring.colourPixel((w - getWidth() / 2) / (getWidth() / scale), (getHeight() / 2 - h) / (getHeight() / scale), iterations));
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
