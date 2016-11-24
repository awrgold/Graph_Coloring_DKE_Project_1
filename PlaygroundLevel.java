import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class PlaygroundLevel extends Level {

	public PlaygroundLevel(GameState state){
		super(state, Graph.generateRandomGraph(4, 4));
	} // (vertices, edges)

	public void draw(Graphics g) {
		graph.draw(g);
	}

	public void tick() {}
}
