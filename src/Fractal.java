public class Fractal {
    private final double order;

    /**
     * Construct a new Fractal based on the Mandelbrot set,
     * with the iterable function f<sub>c</sub>(z) = z<sup>d</sup> + c
     * <p>
     * This constructor has a time complexity of O(n<sup>2</sup>), where n is the order of the fractal
     *
     * @param order the value of d in the iterable function
     */
    public Fractal(double order) {
        this.order = order;
    }

    /**
     * Iterate a complex number over the function defined by this fractal.
     * Return the number of iterations required for the value to reach a critical value of 2.
     * <p>
     * This method has a worst-case time complexity of O(n*d), where n is the number of iterations, and d is the order
     *
     * @param c the complex number to be iterated through z<sup>2</sup> + c.
     * @param iterations the number of iterations this method will run to determine if the complex number is
     * @return the colour value of each pixel on the screen
     */
    public double iterate(Complex c, int iterations) {
        Complex z = new Complex(c.getReal(), c.getImaginary());
        int i = 0;
        for(; i < iterations && z.magnitudeSquared() < 4; i++) {
            z.pow(order).add(c);
        }
        return ratio(z, i, iterations);
    }

    private double ratio(Complex c, int iterations, int maxIterations) {
        if (iterations < maxIterations) {
            return (1 + iterations - Math.log(Math.log(c.magnitudeSquared()) / (2 * Math.log(2))) / Math.log(order));
        }
        return 0;
    }

    /**
     * Return the order of this {@code Fractal}
     * <p>
     * This method has a time complexity of O(1).
     *
     * @return The order of this fractal.
     */
    public double order() {
        return order;
    }

}
