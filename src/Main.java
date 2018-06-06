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
    }

}
