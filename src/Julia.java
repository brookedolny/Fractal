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

    private double ratio(Complex c, int iterations, int maxIterations) {
        if (iterations < maxIterations) {
            return iterations + 1 - Math.log((Math.log(c.magnitudeSquared()) / 2) / Math.log(2)) / Math.log(order);
        }
        return 0;
    }

    double order() { return order; }

}
