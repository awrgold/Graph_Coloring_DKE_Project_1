import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.JLabel;

public class BitterEnd extends GameMode{

    private BitterEndHUD hud;

    private int seconds;
    private int millis;

    public BitterEnd(GameState state, Graph graph){
        super(state,graph);
        hud = new BitterEndHUD();
        addHUD(hud);
        seconds = 0;
        millis = 0;

    }

    @Override
    protected void tick() {
        millis++;
        hud.putTime();
        if (millis == 60) {
            seconds++;
            millis = 0;
        }
    }

    private String showHint() {
        return "Give up.";
    }

    private class BitterEndHUD extends HUD {
        private JLabel time;

        private BitterEndHUD(){
            setLayout(new BorderLayout());
            JButton hintButton = new JButton("Gimme a HINT!");
            JLabel hintText = new JLabel();
            hintText.setFont(new Font("Comic Sans MS", Font.ITALIC, 22));
            time = new JLabel();
            //LAMBDA <3
            hintButton.addActionListener(e -> hintText.setText(showHint()));
            add(hintButton, BorderLayout.WEST);
            add(hintText, BorderLayout.CENTER);
            add(time, BorderLayout.EAST);
        }

        void putTime() {
            time.setText(seconds + " seconds since you started. CHOP CHOP, NOW !!!" );
        }

        @Override
        public void draw(Graphics2D g) {}
    }
}
