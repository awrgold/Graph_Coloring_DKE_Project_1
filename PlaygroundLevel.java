import java.awt.Graphics2D;

public class PlaygroundLevel extends Level {

	public PlaygroundLevel(GameState state){
		super(state, Graph.generateRandomGraph(15,60));
	}
	public void draw(Graphics2D g) {
		graph.draw(g);
	}

	public void tick() {}
}
