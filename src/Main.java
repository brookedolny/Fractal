import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        final int LENGTH = 5000; // change this to change the resolution.
        final int ORDER = 2; // change this to change the order.
        String name = "Mandelbrot Set Panel";
        long time = System.currentTimeMillis();
        // initial GUI set up
//        JFrame frame = new JFrame();
//        frame.setName("Mandelbrot Set");
//        MandelbrotSetPanel panel = new MandelbrotSetPanel(ORDER);
//        frame.setName(name);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.add(panel);
//        frame.setSize(LENGTH,LENGTH);
//        panel.setSize(LENGTH,LENGTH);
//        System.out.println("width: "+panel.getWidth()+" height: "+panel.getHeight());
//        frame.setVisible(true);
//        frame.setResizable(false);


//        JuliaSetImage image = new JuliaSetImage(0, -0.8, 5000, 1000, 2, 4);
        MandelbrotSetImage image = new MandelbrotSetImage( 1000, 1000, 6, 4);
        image.drawImage();
        System.out.println("time to draw image: " + (System.currentTimeMillis() - time));
        image.saveImage();
    }

}
