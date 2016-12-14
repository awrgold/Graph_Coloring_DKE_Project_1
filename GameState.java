
public class GameState {

	public static final int MAIN_MENU = 0;
	public static final int INGAME = 1;
	public static final int PAUSE_MENU = 2;
	public static final int GAME_OVER = 3;

	public Game game;

	private int currentState;
	public Level[] states = new Level[4];

	public int getState(){
		return currentState;
	}

	public void setState(int state){
		currentState = state;
	}


	public Level getActiveLevel(){
		return states[currentState];
	}
}
