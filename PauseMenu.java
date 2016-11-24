import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PauseMenu extends Level {

	private int clickedVertex;
	private int vmOffsetX;
	private int vmOffsetY;
	private MenuVertex[] items;

	public PauseMenu(GameState state) {
		super(state,null);
		clickedVertex = -1;
		items = new MenuVertex[4];
		items[0] = new MenuVertex(Game.WIDTH/4, Game.HEIGHT/2, "   Save");		// Spaces in string(s) to offset the text to be perfectly above the vertex.
		items[1] = new MenuVertex(625, Game.HEIGHT/2, "   Quit");				// @ X=675 pixels, because double/fraction == lossy conversion. Need to find a way to...
		items[2] = new MenuVertex(625, Game.HEIGHT/4, " Restart");				// ...find screen ratios without throwing an error.
		items[3] = new MenuVertex(Game.WIDTH/4, Game.HEIGHT/4, " Resume");
		graph = new Graph(items, new int[][]{new int[]{0,1}, new int[]{1,2}, new int[]{2,3}, new int[]{3,0}});
		// TODO Auto-generated constructor stub
	}
	public void mousePressed(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		clickedVertex = graph.getVertexAt(x, y);
		if(clickedVertex != -1){
			if(e.getButton() == MouseEvent.BUTTON3){
				System.out.println("BUTTON 3!");
				Vertex v = graph.getVertex(clickedVertex);
				vmOffsetX = x-v.getX();
				vmOffsetY = y-v.getY();
			} else if(e.getButton() == MouseEvent.BUTTON1){
				//insert here what the menu items have to do
				//start a level
				if (clickedVertex == 0) { // TODO: If they click "Save," save the current state of the game to a file in the game folder.
				}
				if (clickedVertex == 1) { // If they click "Quit," send them back to the start menu.
					state.setState(GameState.MAIN_MENU);
				}
				if(clickedVertex == 2){ // TODO If they click "Restart," generate the same graph, back at square 1. (How???)
					state.states[GameState.INGAME] = new PlaygroundLevel(state);
					state.setState(GameState.INGAME);
				}
				if (clickedVertex == 3) { // If they click "Resume," resume same game with no changes.
					state.setState(GameState.INGAME);
				}
				clickedVertex = -1;
			}
		}
	}
	@Override
	public void draw(Graphics g) {
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
