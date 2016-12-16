import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public abstract class State extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

	protected GameState gamestate;

	public State(GameState gamestate){
		this.gamestate = gamestate;
		setFocusable(true);
		setRequestFocusEnabled(true);
	}

	public abstract void exit();
	public abstract void enter();
	//override all event handling methods, so that this has not to be done by all subclasses
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}

	public void mouseMoved(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}

	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}