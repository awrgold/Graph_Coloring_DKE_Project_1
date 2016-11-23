import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class PauseMenu extends Level {

	public PauseMenu(GameState state) {
		super(state,null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		g.drawString("PAUSE MENU", Game.WIDTH/2, Game.HEIGHT/2);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			state.setState(GameState.INGAME);
	}
}
