import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class PlaygroundLevel extends Level {

	protected ColorSelectionMenu csm;

	public PlaygroundLevel(GameState state){
		super(state, GraphUtil.generateRandomGraph(15,60));
	}

	public PlaygroundLevel(GameState state, Graph g){
		super(state,g);
		//menu = new ColorSelectionMenu(graph.getVertex(0), new int[]{0,1,2,3});

	}

	public void draw(Graphics2D g) {
		graph.draw(g);
		if(csm != null)
			csm.draw(g);
	}

	public void mousePressed(MouseEvent e){
		super.mousePressed(e);
		if(clickedVertex != -1 && e.getButton() == MouseEvent.BUTTON1){
 			csm = new ColorSelectionMenu(graph.getVertex(clickedVertex),new int[]{0,1,2,3,4,5,6});
 		}
	}

	public void mouseReleased(MouseEvent e){
		if(clickedVertex != -1 && e.getButton() == MouseEvent.BUTTON1){
			Vertex v = graph.getVertex(clickedVertex);
			v.setColor(csm.getSelection(e.getX(),e.getY()));
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

	public void tick(){}
}
