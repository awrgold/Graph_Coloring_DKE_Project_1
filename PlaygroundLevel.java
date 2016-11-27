import java.awt.Graphics2D;

public class PlaygroundLevel extends Level {

	public PlaygroundLevel(GameState state){
		super(state, Graph.generateRandomGraph(15,60));
	}
	public PlaygroundLevel(GameState state, int n, int m){
		super(state, Graph.generateRandomGraph(n,m));
	}
	public PlaygroundLevel(GameState state, String[] file){
		super(state, Graph.readGraphFromFile(file));
	}
	public void draw(Graphics2D g) {
		graph.draw(g);
	}

	public void tick() {}
}
