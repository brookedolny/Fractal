import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        long time;
        for(double d = 2.0; d < 6; d += 0.01) {
//            JuliaSetImage image = new JuliaSetImage( 0, -0.8, 1000, 1000, d, 4);
            time = System.currentTimeMillis();
            MandelbrotSetImage image = new MandelbrotSetImage(1000, 1000, d, 4);
            image.drawImage();
            System.out.println("time to draw image: " + (System.currentTimeMillis() - time));
            image.saveImage("./MandelbrotImages");
        }
    }

}
