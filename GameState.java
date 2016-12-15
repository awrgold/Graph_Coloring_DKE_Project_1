
public class GameState {

	public static final int MAIN_MENU = 0;
	public static final int INGAME = 1;
	public static final int PAUSE_MENU = 2;
	public static final int ENDGAME_SCREEN =3;
	private Game game;

	private int currentState;
	private State[] states = new State[4];
	public GameState(Game game) {
		this.game = game;
	}
	public void setInitialState(State initialState, int initialStateID){
        states[initialStateID] = initialState;
        currentState = initialStateID;
        game.add(initialState);

	}
    public void enterInitialState(){
	    states[currentState].enter();
    }
	public void changeState(int state) {
        states[currentState].exit();
        game.remove(states[currentState]);
		currentState = state;
		states[currentState].enter();
		game.add(states[currentState]);
		game.pack();
	}
	public void replaceState(State state, int number){
		if (states[number] != null) states[number].exit();
		states[number] = state;
	}
	public State getActiveState(){

	return states[currentState];
	}
	public State getState(int n){
		return states[n];
	}
}
