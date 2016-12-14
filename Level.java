import static constants.Drawing.*;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.sound.sampled.*;
import java.io.File;

public abstract class Level extends MouseAdapter implements KeyListener{

	protected GameState state;
	protected Graph graph;
	protected int clickedVertex;
	protected int lastHoveredVertex;
	protected boolean isDragging;
    //vertex-mouse-offset: the offset of the mouse from the center of the vertex (used so that the vertex doesn't "jump" when starting to drag
	private int vmOffsetX;
	private int vmOffsetY;
	private AudioPlayer blip1;
	private AudioPlayer blip2;
	private boolean doBlip;
	protected AudioPlayer elevMusic;


	public Level(GameState state, Graph graph){
		this.state = state;
		this.graph = graph;
		clickedVertex = -1;
		lastHoveredVertex = 0;
		isDragging = false;
		elevMusic = new AudioPlayer("/resources/Music/Elevator.wav");
		blip1 = new AudioPlayer("/resources/SFX/blip 1.wav");
		blip2 = new AudioPlayer("/resources/SFX/blip 2.wav");
	}

	public void terminate(){
		state.setState(GameState.MAIN_MENU);
		state.states[GameState.INGAME] = null;
	}
	public void setGraph(Graph graph){
		this.graph = graph;
	}


	public abstract void draw(Graphics2D g);
	public void tick(){}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			state.setState(GameState.PAUSE_MENU);
			if (state.getState() == 2) {
				elevMusic.play();
			}
		}
	}

	public void mouseMoved(MouseEvent e){
		int currHoveredVertex = graph.getVertexAt(e.getX(), e.getY());
		if(currHoveredVertex != lastHoveredVertex)
			graph.getVertex(lastHoveredVertex).highlight(false);
		if(currHoveredVertex == -1) doBlip = true;
		if(currHoveredVertex != -1){
			Vertex v = graph.getVertex(currHoveredVertex);
			if(doBlip) blip2.play();
			doBlip = false;
			v.highlight(true);
			lastHoveredVertex = currHoveredVertex;
			// TODO - plays every time you hover, needs to play only once.

		}
	}

	public void mousePressed(MouseEvent e){
		clickedVertex = graph.getVertexAt(e.getX(), e.getY());
		if(clickedVertex != -1){
			blip1.play();

			//super.mousePressed allows me to reference this class from a subclass
			//and make individual changes based on the class (on top of what already exists)
			Vertex v = graph.getVertex(clickedVertex);
    		vmOffsetX = e.getX() - v.getX();
     		vmOffsetY = e.getY() - v.getY();
			if(e.getButton() == MouseEvent.BUTTON3){
     			isDragging = true;
     		}
		}
	}

	public void mouseDragged(MouseEvent e){
		if(clickedVertex != -1 && isDragging){
			int newX = e.getX() - vmOffsetX;
			int newY = e.getY() - vmOffsetY;
			Vertex v = graph.getVertex(clickedVertex);    // TODO: 12/12/2016 Should we not do this internally?
			if(newX < GRAPH_SPACE.getX())
				newX = (int) GRAPH_SPACE.getX();
			if(newX > GRAPH_SPACE.getMaxX() - v.getDiameter())
				newX = (int) GRAPH_SPACE.getMaxX() - v.getDiameter();
			if(newY < GRAPH_SPACE.getY())
				newY = (int) GRAPH_SPACE.getY();
			if(newY > GRAPH_SPACE.getMaxY() - v.getDiameter())
				newY = (int) GRAPH_SPACE.getMaxY() - v.getDiameter();
			v.move(newX, newY);
		}
	}

	public void mouseReleased(MouseEvent e){
		clickedVertex = -1;
		if(e.getButton() == MouseEvent.BUTTON3){
			isDragging = false;
		}
	}
//  Implementation for these methods can be added.
//	public void mouseClicked(MouseEvent e){}
//	public void mouseEntered(MouseEvent e){}
//	public void mouseExited(MouseEvent e) {}
}