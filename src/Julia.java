public class Julia {
    private final double REAL;
    private final double IMAGINARY;
    private final int ORDER;
    private double[] coefficients;
    private int iterations;

    public Julia(double real, double imaginary, int order) {
        REAL = real;
        IMAGINARY = imaginary;
        ORDER = order;
        coefficients = new double[order + 1];
        if (order > 2) {
            for(int i = 0; i < order + 1; i++) {
                coefficients[i] = choose(order, i);
            }
        }

    }

    public double iterate(double real, double imaginary, int iterations) {
        // TODO higher order sets
        this.iterations = iterations;
        if (ORDER == 2) {
            return orderTwo(real, imaginary, iterations);
        }
        return 0;
    }

    /**
     * A Julia Set Image for a quadratic polynomial.
     * @param real The real part of the initial value of the iterative complex number
     * @param imaginary The imaginary part of the initial value of the iteratove complex number
     * @param iterations The maximum number of iterations to iterate the
     * @return the ratio corresponding to a complex number and it's value before being greater than or equal to two
     */
    private double orderTwo(double real, double imaginary, int iterations) {
        double z;
        double zreal = real;
        double zimaginary = imaginary;
        if (Math.abs(real) >= 2 || Math.abs(imaginary) >= 2)
            return ratio(real,imaginary, 0, iterations);
        for(int i = 0; i < iterations; i++) {
            z = zreal;
            zreal = zreal*zreal - zimaginary*zimaginary + REAL;
            zimaginary = 2*z*zimaginary + IMAGINARY;
            if (Math.abs(zreal*zreal) >= 2 || Math.abs(zimaginary*zimaginary) >= 2)
                return ratio(real, imaginary, i, iterations);
        }
        return 0;
    }


    private double ratio(double real, double imaginary, int iterations, int maxIterations) {
        if (iterations < maxIterations) {
            return iterations + 1 - Math.log((Math.log(real*real + imaginary*imaginary) / 2) / Math.log(2)) / Math.log(ORDER);
        }
        return 0;
    }

    private int choose(int n, int k) {
        int choose = 1;
        for (int i = k + 1; i <= n; i++) {
            choose *= i;
        }
        for (int i = 1; i <= (n - k); i++) {
            choose /= i;
        }
        return choose;
    }

    int order() { return ORDER; }

    int getIterations() { return iterations; }
}
