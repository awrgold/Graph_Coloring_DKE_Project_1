import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

public abstract class Level extends MouseAdapter implements KeyListener{

	protected GameState state;
	protected Graph graph;
	public Level(GameState state, Graph graph){
		this.state = state;
		this.graph = graph;
	}
	public void terminate(){
		state.setState(GameState.MAIN_MENU);
		
	}
	public abstract void draw(Graphics g);
	public abstract void tick();
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			state.setState(GameState.PAUSE_MENU);
	}
	public void keyReleased(KeyEvent e) {}
}