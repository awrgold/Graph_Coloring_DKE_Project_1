import java.awt.BorderLayout;
import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.JLabel;

public class UpperBound extends GameMode{

    private UpperBoundHUD hud;

    private int timeLimit;
    private int millis;

    public UpperBound(GameState state, Graph graph){
        super(state,graph);
        hud = new UpperBoundHUD();
        addHUD(hud);
        millis = 0;
        timeLimit = 3;

    }

    @Override
    protected void tick() {
        millis++;
        hud.putTime();
        if (millis == 60) {
            timeLimit--;
            millis = 0;
        }
        if (timeLimit == 0) {
            if (getGraph().isFullyColored()) {
                getGameState().replaceState(new GameOverScreen(gamestate, "Incredible!", "Good job!",
                                            "Oh, NO, WHAT'S THAT????!", "IT'S THE KEELK!!!RUUN!11!!" ), GameState.ENDGAME_SCREEN);
                getGameState().changeState(GameState.ENDGAME_SCREEN);
                System.out.println("yes");
            } else {
                getGameState().replaceState(new GameOverScreen(gamestate, "You're no match for Kelk", "Now perish!"), GameState.ENDGAME_SCREEN);
                getGameState().changeState(GameState.ENDGAME_SCREEN);
                System.out.println("no");
            }
        }
    }

    private String showHint() {
        return "Nope..";
    }

    private class UpperBoundHUD extends HUD {
        private JLabel time;

        private UpperBoundHUD(){
            setLayout(new BorderLayout());
            JButton hintButton = new JButton("Gimme a HINT!");
            JLabel hintText = new JLabel();
            time = new JLabel();
            hintButton.addActionListener(e -> hintText.setText(showHint()));
            add(hintButton, BorderLayout.WEST);
            add(hintText, BorderLayout.CENTER);
            add(time, BorderLayout.EAST);
        }

        void putTime() {
            time.setText(timeLimit + " seconds to your demise! Color the graph to save your soul." );
        }

        @Override
        public void draw(Graphics2D g) {}
    }
}
