import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Level extends MouseAdapter implements KeyListener{

	protected GameState state;
	protected Graph graph;
	private int clickedVertex;
	private int vmOffsetX;
	private int vmOffsetY;
	public Level(GameState state, Graph graph){
		this.state = state;
		this.graph = graph;
	}
	public void terminate(){
		state.setState(GameState.MAIN_MENU);
		
	}
	public void setGraph(Graph graph){
		this.graph = graph;
	}
	public abstract void draw(Graphics g);
	public abstract void tick();
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			state.setState(GameState.PAUSE_MENU);
	}
	public void keyReleased(KeyEvent e) {}
	public void mouseDragged(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		clickedVertex = graph.getVertexAt(x, y);
		if(clickedVertex != -1){
			System.out.println("DRAG");
			int newX = e.getX()-vmOffsetX;
			int newY = e.getY()-vmOffsetY;
			Vertex v = graph.getVertex(clickedVertex);
			//some case handeling if the mouse is off screen (prevents the vertex from moving off-screen as well)
			//WEIRD: one can move a vertex slightly closer to the border on the left-hand side than on the right-hand side.
			if(newX < -v.getDiameter()/2)
				newX = -v.getDiameter()/2;
			if(newX > Game.WIDTH-v.getDiameter()/2)
				newX = Game.WIDTH-v.getDiameter()/2;
			if(newY < -v.getDiameter()/2)
				newY = -v.getDiameter()/2;
			//why 1.4? cuz i dunno, to prevent above mentioned weirdness.
			if(newY > Game.HEIGHT-v.getDiameter()*1.4) newY = (int)
					(Game.HEIGHT-v.getDiameter()*1.4);
			graph.getVertex(clickedVertex).move(newX, newY);
		}
	}
}