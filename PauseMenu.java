import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.sound.sampled.*;
import java.io.File;

public class PauseMenu extends Level {

	private AudioPlayer blip1;

	public PauseMenu(GameState state) {
		super(state,null);
		MenuVertex[] items;
		clickedVertex = -1;
		items = new MenuVertex[4];
		items[3] = new MenuVertex(Game.WIDTH/5, Game.HEIGHT/5, "Resume");
		items[2] = new MenuVertex((Game.WIDTH*2)/3, Game.HEIGHT/5, "Restart");
		items[0] = new MenuVertex(Game.WIDTH/5, Game.HEIGHT*2/3, "Save");		// Spaces in string(s) to offset the text to be perfectly above the vertex.
		items[1] = new MenuVertex((Game.WIDTH*2)/3, Game.HEIGHT*2/3, "Quit");
		graph = new Graph(items, new int[][]{new int[]{2}, new int[]{3}, new int[]{}, new int[]{}});
		blip1 = new AudioPlayer("/resources/SFX/blip 1.wav");


	}
	public void mousePressed(MouseEvent e){
		super.mousePressed(e);
		if(e.getButton() == MouseEvent.BUTTON1){
			if (clickedVertex != -1){
				blip1.play();
				elevMusic.stop();
			}
			if (clickedVertex == 0) { // TODO: If they click "Save," save the current state of the game to a file in the game folder.
			}
			if (clickedVertex == 1) { // If they click "Quit," send them back to the start menu.
				state.setState(GameState.MAIN_MENU);
			}
			if(clickedVertex == 2){ // TODO add a restart() function to the Level class
				state.states[GameState.INGAME] = new PlaygroundLevel(state,GraphUtil.generateRandomGraph(3, 3));
				state.setState(GameState.INGAME);
			}
			if (clickedVertex == 3) { // If they click "Resume," resume same game with no changes.
				state.setState(GameState.INGAME);
			}
			clickedVertex = -1;
		}
	}
	// TODO: implement background image for pause menu here.
	@Override
	public void draw(Graphics2D g) {
		g.setFont(new Font("Pause Menu Font", Font.BOLD, 24));
		g.drawString("PAUSED", (Game.WIDTH/2)-25, (Game.HEIGHT/2)-15);
		graph.draw(g);
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
