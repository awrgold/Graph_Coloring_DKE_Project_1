import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GameOverScreen extends State {


    public GameOverScreen(GameState gamestate, String... gameOverInfo) {
        super(gamestate);
        init(gameOverInfo);
    }

    private void init(String[] gameOverInfo) {
        setBackground(Color.BLACK);
        setBorder(new EmptyBorder(4, 4, 4, 4));
        setLayout(new BorderLayout());
        JButton exitBtn = new JButton("Exit");
        exitBtn.setPreferredSize(new Dimension(100, 50));
        exitBtn.addActionListener(e -> gamestate.changeState(GameState.MAIN_MENU));
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(exitBtn, BorderLayout.EAST);
        buttonPanel.setOpaque(false);
        JLabel gameOver = new JLabel("Game Over . . .", SwingConstants.CENTER);
        gameOver.setFont(new Font("Calibri Bold", Font.BOLD, 40));
        gameOver.setForeground(new Color(239, 75, 0));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        Random r;
        r = new Random();
        for (int i = 0; i < gameOverInfo.length; i++) {
            JLabel tmp = new JLabel(gameOverInfo[i]);
            tmp.setFont(new Font("Calibri Bold", Font.BOLD, 35));
            tmp.setForeground(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
            tmp.setHorizontalTextPosition(SwingConstants.CENTER);
            infoPanel.add(tmp);
//            infoPanel.getComponent(i).setFont(new Font("Calibri Bold", Font.BOLD, 25));
//            infoPanel.getComponent(i).setForeground(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
//            infoPanel.getComponent(i).setVerticalAlignment(Component.CENTER_ALIGNMENT);


        }

        add(buttonPanel, BorderLayout.SOUTH);
        add(infoPanel, BorderLayout.CENTER);
        add(gameOver, BorderLayout.NORTH);
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
    }


    @Override
    public void exit(){}
    @Override
    public void enter(){}
}
