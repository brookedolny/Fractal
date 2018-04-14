public class Julia {
    private Complex c;
    private double order;

    public Julia(Complex c, double order) {
        this.c = c;
        this.order = order;
    }

    public double iterate(Complex z, int iterations) {
        int i = 0;
        for(; i < iterations && z.magnitudeSquared() < 4; i++) {
            z.pow(order).add(c);
        }
        return ratio(z, i, iterations);
    }

    /**
     * A Julia Set Image for a quadratic polynomial.
     * @param c the complex number to
     * @param iterations The maximum number of iterations to iterate the
     * @return the ratio corresponding to a complex number and it's value before being greater than or equal to two
     */
//    private double orderTwo(Complex c, int iterations) {
//        Complex z = new Complex(c.getReal(), c.getImaginary());
//        int i = 0;
//        for(; i < iterations && z.magnitudeSquared() < 4; i++) {
//            z = zreal;
//            zreal = zreal*zreal - zimaginary*zimaginary + REAL;
//            zimaginary = 2*z*zimaginary + IMAGINARY;
//            if (Math.abs(zreal*zreal) >= 2 || Math.abs(zimaginary*zimaginary) >= 2)
//                return ratio(real, imaginary, i, iterations);
//        }
//        return 0;
//    }


    private double ratio(Complex c, int iterations, int maxIterations) {
        if (iterations < maxIterations) {
            return iterations + 1 - Math.log((Math.log(c.magnitudeSquared()) / 2) / Math.log(2)) / Math.log(order);
        }
        return 0;
    }

    double order() { return order; }

}
