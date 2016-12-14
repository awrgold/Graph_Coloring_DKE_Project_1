import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import static constants.Drawing.*;

public class PauseMenu extends Level {

	private AudioPlayer blip1;

	public PauseMenu(GameState state) {
		super(state,null);
		MenuVertex[] items;
		clickedVertex = -1;
		items = new MenuVertex[4];
		items[0] = new MenuVertex((int) GRAPH_SPACE.getWidth()/4, (int) GRAPH_SPACE.getHeight()*3/4, "   Save");		// Spaces in string(s) to offset the text to be perfectly above the vertex.
		items[1] = new MenuVertex(((int) GRAPH_SPACE.getWidth()*2)/3, (int) GRAPH_SPACE.getHeight()*3/4, "   Quit");
		items[2] = new MenuVertex(((int) GRAPH_SPACE.getWidth()*2)/3, (int) GRAPH_SPACE.getHeight()/4, " Restart");
		items[3] = new MenuVertex((int) GRAPH_SPACE.getWidth()/4, (int) GRAPH_SPACE.getHeight()/4, " Resume");
		graph = new Graph(items, new int[][]{new int[]{1,3}, new int[]{2}, new int[]{3}, new int[]{}});
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
				elevMusic.stop();
			}
			if(clickedVertex == 2){ // Reset the coloring
				Graph inGameGraph = PlaygroundLevel.getPlayGraph(); //Grab the graph the user played with.
				inGameGraph.decolorGraph(); //Reset the colors of this graph
				state.setState(GameState.INGAME); //Return to the playgroundlevel
			}
			if (clickedVertex == 3) { // If they click "Resume," resume same game with no changes.
				state.setState(GameState.INGAME);
				elevMusic.stop();
			}
			clickedVertex = -1;
		}
	}
	// TODO: implement background image for pause menu here.
	@Override
	public void draw(Graphics2D g) {
		g.setFont(new Font("Pause Menu Font", Font.BOLD, 24));
		g.drawString("PAUSED", ((int) GRAPH_SPACE.getWidth()/2)-25, ((int) GRAPH_SPACE.getHeight()/2)-15);
		graph.draw(g);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			state.setState(GameState.INGAME);
			elevMusic.stop();
			System.out.println("WTF, Why won't you stop??");
		}
	}
}
