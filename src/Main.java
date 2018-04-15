import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();

        MandelbrotSetImage image = new MandelbrotSetImage(
            1920*2,
            1080*2,
            10000,
            2,
            -0.743643887037151,
            0.13182590420533,
            2.00e-11);

        image.drawImage();
        System.out.println("time to draw image: " + (System.currentTimeMillis() - time));
        image.saveImage("./");
//        for(double d = 2.0; d < 6; d += 0.01) {
//            JuliaSetImage image = new JuliaSetImage( 0, -0.8, 1000, 1000, d, 4);
//            time = System.currentTimeMillis();
//            MandelbrotSetImage image = new MandelbrotSetImage(1000, 100, d, 1);
//            image.drawImage();
//            System.out.println("time to draw image: " + (System.currentTimeMillis() - time));
//            image.saveImage("./");
//        }
    }

}
