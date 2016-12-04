import javax.swing.*;
import java.awt.*;

public class Window {

    private static final int OFFSETX = 14;
    private static final int OFFSETY = 37;

    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);
        Dimension correctSize = new Dimension(width + OFFSETX, height + OFFSETY);

        frame.setPreferredSize(correctSize);
        frame.setMaximumSize(correctSize);
        frame.setMinimumSize(correctSize);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }

}
