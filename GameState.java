
public class GameState {

	public static final int MAIN_MENU = 0;
	public static final int INGAME = 1;
	public static final int PAUSE_MENU = 2;

	public Game game;

	private int currentState;
	public Level[] states = new Level[3];
	public void setState(int state){
		currentState = state;
	}
	public Level getActiveLevel(){
		return states[currentState];
	}
}
