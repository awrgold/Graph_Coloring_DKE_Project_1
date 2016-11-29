import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PauseMenu extends Level {

	public PauseMenu(GameState state) {
		super(state,null);
		MenuVertex[] items;
		clickedVertex = -1;
		items = new MenuVertex[4];
		items[0] = new MenuVertex(Game.WIDTH/4, Game.HEIGHT/2, "   Save");		// Spaces in string(s) to offset the text to be perfectly above the vertex.
		items[1] = new MenuVertex((Game.WIDTH*2)/3, Game.HEIGHT/2, "   Quit");
		items[2] = new MenuVertex((Game.WIDTH*2)/3, Game.HEIGHT/4, " Restart");
		items[3] = new MenuVertex(Game.WIDTH/4, Game.HEIGHT/4, " Resume");
		graph = new Graph(items, new int[][]{new int[]{1,3}, new int[]{2}, new int[]{3}, new int[]{}});

	}
	public void mousePressed(MouseEvent e){
		super.mousePressed(e);
		if(e.getButton() == MouseEvent.BUTTON1){
			if (clickedVertex == 0) { // TODO: If they click "Save," save the current state of the game to a file in the game folder.
			}
			if (clickedVertex == 1) { // If they click "Quit," send them back to the start menu.
				state.setState(GameState.MAIN_MENU);
			}
			if(clickedVertex == 2){ // TODO add a restart() function to the Level class
				state.states[GameState.INGAME] = new PlaygroundLevel(state,Graph.generateRandomGraph(3, 3));
				state.setState(GameState.INGAME);
			}
			if (clickedVertex == 3) { // If they click "Resume," resume same game with no changes.
				state.setState(GameState.INGAME);
			}
			clickedVertex = -1;
		}
	}
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
