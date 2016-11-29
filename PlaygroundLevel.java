import java.awt.Graphics2D;

public class PlaygroundLevel extends Level {

	public PlaygroundLevel(GameState state, Graph g){
		super(state,g);
	}
	public void draw(Graphics2D g) {
		graph.draw(g);
	}

	public void tick() {}
}
