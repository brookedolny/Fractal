public class Fractal {
    private final int order;
    private double[] coefficients;

    /**
     * Construct a new Fractal based on the Mandelbrot set,
     * with the iterable function f<sub>c</sub>(z) = z<sup>d</sup> + c
     * <p>
     * This constructor has a time complexity of O(n<sup>2</sup>), where n is the order of the fractal
     *
     * @param order the value of d in the iterable function
     */
    public Fractal(int order) {
        this.order = order;
        coefficients = new double[order + 1];
        // initialize the coefficients
        for (int i = 0; i < coefficients.length; i++) {
            if ((i / 2) % 2 == 0) {
                coefficients[i] = choose(order, i);
            } else {
                coefficients[i] = -1 * choose(order, i);
            }
        }
    }

    /**
     * Iterate a complex number over the function defined by this fractal.
     * Return the number of iterations required for the value to reach a critical value of 2.
     * <p>
     * This method has a worst-case time complexity of O(n*d), where n is the number of iterations, and d is the order
     *
     * @param real the real part of the value to be iterated
     * @param imaginary the imaginary part
     * @param iterations the number of iterations this method will run to determine if the complex number is
     * @return the colour value of each pixel on the screen
     */
    public double iterate(double real, double imaginary, int iterations) {
        if (order == 2) {
            return orderTwo(real, imaginary, iterations);
        }
        // real and imaginary parts of iterable value
        double zreal = real;
        double zimaginary = imaginary;
        // multiples of real and imaginary part for binomial expansion (complex number multiplication)
        double[] realMult = new double[order + 1];
        double[] imagMult = new double[order + 1];
        double sum1, sum2; // sum1 is the real portion, sum2 is the imaginary portion

        if (Math.abs(real) >= 2 || Math.abs(imaginary) >= 2 || real*real + imaginary*imaginary >= 4) {
            return 1; // will produce a black pixel
        }

        // TODO make this more efficient using storage of previous powers
        realMult[0] = 1;
        imagMult[0] = 1;
        for (int n = 1; n <= order; n++) {
            realMult[n] = realMult[n - 1] * zreal;
            imagMult[n] = imagMult[n - 1] * zimaginary;
        }

        for (int i = 1; i < iterations; i++) {
            sum1 = 0; sum2 = 0;
            for (int j = 0; j <= order; j++) {
                // terms in the binomial expansion alternate being real and imaginary. if j % 2 == 0, it is real, else, it is imaginary.
                if (j % 2 == 0) {
                    sum1 += coefficients[j] * realMult[order - j] * imagMult[j];
                } else {
                    sum2 += coefficients[j] * realMult[order - j] * imagMult[j];
                }
            }

            zreal = sum1 + real;
            zimaginary = sum2 + imaginary;

            realMult[0] = 1;
            imagMult[0] = 1;
            for (int n = 1; n <= order; n++) {
                realMult[n] = realMult[n - 1] * zreal;
                imagMult[n] = imagMult[n - 1] * zimaginary;
            }

            if (Math.abs(zreal) >= 2 || Math.abs(zimaginary) >= 2 || zreal*zreal + zimaginary*zimaginary >= 4) {
                return ratio(zreal, zimaginary, i, iterations);
            }
        }
        return 0; // will produce a white pixel (i.e. point is 'in' the Mandelbrot set for the number of iterations)
    }

    /**
     * Iterate through the function z<sup>2</sup>+c for a complex number c, starting with z = 0.
     * This method preforms the same function as {@link #iterate(double, double, int)}, for an order of 2.
     * <p>
     * This method has a worst-case time complexity of O(n), where n is the number of iterations
     *
     * @param real The real part of the complex number c
     * @param imaginary The imaginary part of the complex number c
     * @param iterations The number of iterations to preform per pixel to determine if a number is part of the Man
     * @return The colour of the pixel represented by the complex number c
     * @see #iterate(double, double, int) iterate(double, double, int) for the generalized version of this method
     */
    public double orderTwo(double real, double imaginary, int iterations) {
        double zreal = 0;
        double zimaginary = 0;
        double realsq = real * real;
        double imagsq = imaginary * imaginary;

        if (Math.abs(real) >= 2 || Math.abs(imaginary) >= 2) {
            return ratio(zreal, zimaginary, 0, iterations);
        } else if (imaginary == 0) {
            for (int i = 0; i < iterations; i++) {
                zreal = zreal * zreal + real;
                if (zreal >= 2) {
                    return ratio(zreal, zimaginary, i, iterations);
                }
            }
            return 0;
        } else {
            zreal = real;
            zimaginary = imaginary;
            if (realsq + imagsq >= 4) {
                return ratio(zreal, zimaginary, 0, iterations);
            }
            for (int i = 1; i < iterations; i++) {
                zimaginary = 2 * zimaginary * zreal + imaginary;
                zreal = realsq - imagsq + real;

                realsq = zreal * zreal;
                imagsq = zimaginary * zimaginary;

                if (Math.abs(zreal) >= 2 || Math.abs(zimaginary) >= 2 || realsq + imagsq >= 4) {
                    return ratio(zreal, zimaginary, i, iterations);
                }
            }
            return 0;
        }
    }


    /**
     * Assigns a value for each complex number c used as an input into the Mandelbrot Set iterable function,
     * based on the final result after n iterations of the Mandelbrot Set iterable function.
     * <p>
     * This method has a time complexity of O(1).
     *
     * @param real the real part of the complex number z after n iterations of the Mandelbrot Set function
     * @param imaginary the imaginary part of the complex number z after n iterations of the Mandelbrot Set function
     * @param iterations the total number of iterations the complex number c was put through the Mandelbrot Set iterable function
     * @param maxIterations the maximum number of iterations allotted per complex number c
     * @return a value for each complex number c
     */
    private double ratio(double real, double imaginary, int iterations, int maxIterations) {
        if (iterations < maxIterations) {
            return (1 + iterations - Math.log(Math.log(real*real+imaginary*imaginary) / 2) / Math.log(order));
        }
        return 0;
    }

    /**
     * Return the value of nCk, where nCk = n!/(k!(n-k)!)
     * <p>
     * This method has a time complexity of O(n).
     *
     * @param n The n value in the nCk expression, equivalent to the order of the binomial being expanded
     * @param k The k value in the nCk expression, equivalent to the term in the binomial expansion
     * @return The value of n!/(k!(n-k)!)
     */
    private double choose(int n, int k) {
        // TODO improve the efficiency of this
        double choose = 1;

        if (n == k || k == 0) {
            return 1;
        } else if (k == 1) {
            return n;
        }

        for (int i = k + 1; i <= n; i++) {
            choose *= i;
        }
        for (int i = 1; i <= (n - k); i++) {
            choose /= i;
        }

        return choose;
    }

    /**
     * Return the order of this {@code Fractal}
     * <p>
     * This method has a time complexity of O(1).
     *
     * @return The order of this fractal.
     */
    public int order() {
        return order;
    }

}
