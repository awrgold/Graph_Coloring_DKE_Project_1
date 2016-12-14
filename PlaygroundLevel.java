import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class PlaygroundLevel extends Level {

	protected ColorSelectionMenu csm;
	protected static Graph playGraph;
	private int mode;
	protected HUD hud;


	public PlaygroundLevel(GameState state, int v, int e){
		super(state, GraphUtil.generateRandomGraph(v, e));
		hud = new HUD(state.game);
	}

	public PlaygroundLevel(GameState state, Graph g, int mode){
		super(state,g);
		//menu = new ColorSelectionMenu(graph.getVertex(0), new int[]{0,1,2,3});
		playGraph = g;
		this.mode = mode;
		hud = new HUD(state.game);
	}

	/**Created By Jurriaan Berger
	 * has to be static because it's used in a static context in the pause Menu, as far as I can see,
	 * it wouldn't give errors because we have only one PlaygroundLevel at a time.
	 * (and it's static because the IDE says it has to be)
	 * @return the (reference to the) graph that has been used in the playground level
	 */
	public static Graph getPlayGraph(){
		return playGraph;
	}

	public void tick(){
		hud.tick();
	}
	public void draw(Graphics2D g) {
		graph.draw(g);
		if(csm != null)
			csm.draw(g);
		hud.draw(g);
	}

	public void mousePressed(MouseEvent e){
		super.mousePressed(e);
		if(clickedVertex != -1 && e.getButton() == MouseEvent.BUTTON1){
 			csm = new ColorSelectionMenu(graph.getVertex(clickedVertex),graph.getVertexColor(clickedVertex),graph.getAvailableColors(clickedVertex));
 		}
	}

	public void mouseReleased(MouseEvent e){
		if(clickedVertex != -1 && e.getButton() == MouseEvent.BUTTON1){
			Vertex v = graph.getVertex(clickedVertex);
			graph.setVertexColor(clickedVertex, csm.getSelection(e.getX(),e.getY()));
			if(graph.fullyColored()){
				if(mode == 1){
					if(graph.getUsedColors()==graph.getChromaticNR())state.setState(GameState.GAME_OVER); //WATCH OUT: CHROMATIC NUMBER IS NOT YET COMPUTED IN GRAPH
				}else if(mode == 3){

					state.setState(GameState.GAME_OVER); //In case of mode 3,
				}
				state.setState(GameState.GAME_OVER);
			}
			csm = null;
		}
		super.mouseReleased(e);
	}

	public void mouseDragged(MouseEvent e){
		super.mouseDragged(e);
		if(csm != null){
			csm.highlight(e.getX(),e.getY());
		}
	}
}
