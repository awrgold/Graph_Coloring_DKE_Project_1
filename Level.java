import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

public abstract class Level extends MouseAdapter implements KeyListener{
	protected GameState state;
	public Level(GameState state){
		this.state = state;
	}
	protected void terminate(){
		
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