import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class PlaygroundLevel extends Level {
	public PlaygroundLevel(GameState state){
		super(state);
	}
	public void keyPressed(KeyEvent e){
		super.keyPressed(e);
	}
	public void draw(Graphics g) {
		g.drawOval(Game.WIDTH/2, Game.HEIGHT/2, 40, 40);
		
	}

	public void tick() {
		
	}
}
