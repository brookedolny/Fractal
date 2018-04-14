public class Complex {
    private double real;
    private double imaginary;

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex add(Complex x) {
        real += x.real;
        imaginary += x.imaginary;
        return this;
    }

    public Complex multiply(Complex x) {
        double temp = real * x.real - imaginary * x.imaginary;
        imaginary = real * x.imaginary + imaginary * x.real;
        real = temp;
        return this;
    }

    public Complex pow(double power) {
        double radius = Math.pow(magnitudeSquared(), power / 2);
        double angle = (Math.atan2(imaginary, real)) * power;
        real = radius * Math.cos(angle);
        imaginary = radius * Math.sin(angle);
        return this;
    }

    public double getReal() { return real; }

    public double getImaginary() { return imaginary; }

    public double magnitude() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    public double magnitudeSquared() {
        return real * real + imaginary * imaginary;
    }

    public static Complex add(Complex x, Complex y) {
        return new Complex(x.real + y.real, x.imaginary + y.imaginary);
    }

    public static Complex multiply(Complex x, Complex y) {
        return new Complex(x.real * y.real - x.imaginary * y.imaginary, x.real*y.imaginary + x.imaginary * y.real);
    }

    public static Complex pow(Complex x, double power) {
        double radius = Math.pow(x.magnitudeSquared(), power / 2);
        double angle = (Math.atan2(x.imaginary, x.real)) * power;
        return new Complex(radius * Math.cos(angle), radius * Math.sin(angle));
    }

}
