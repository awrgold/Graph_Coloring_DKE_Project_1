
public class GameState {

	public static final int MAIN_MENU = 0;
	public static final int INGAME = 1;
	public static final int PAUSE_MENU = 2;
	AudioPlayer bgMusic1 = new AudioPlayer("/resources/Music/bgMusic1.wav");
	AudioPlayer bgMusic2 = new AudioPlayer("/resources/Music/bgMusic2.wav");
	AudioPlayer elevMusic = new AudioPlayer("/resources/Music/Elevator.wav");

	public Game game;

	private int currentState;
	public Level[] states = new Level[3];

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
