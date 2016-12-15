import javax.swing.*;

public class Game extends JFrame{

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 900;
    public static final int HEIGHT = 600;

    public GameState gamestate;

    public Game() {
        super("Eppstein");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        gamestate = new GameState(this);
        gamestate.replaceState(new PauseMenu(gamestate), GameState.PAUSE_MENU);
		gamestate.replaceState(new GameOverMenu(gamestate), GameState.ENDGAME_SCREEN);
        gamestate.setInitialState(new Menu(gamestate), GameState.MAIN_MENU);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        gamestate.enterInitialState();
    }


    public static void main(String[] args) {
        new Game();
    }

}
