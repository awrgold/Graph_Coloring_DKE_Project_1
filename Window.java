import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.*;

public class Window {


    public Window(String title, Canvas c) {
        JFrame frame = new JFrame(title);


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(c);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
