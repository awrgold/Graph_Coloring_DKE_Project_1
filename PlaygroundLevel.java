import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

// V1.0 Created by Jonas, mouse handlers added by Jurriaan

public class PlaygroundLevel extends Level {
	private int clickedVertex;
	private int vmOffsetX;
	private int vmOffsetY;

	public PlaygroundLevel(GameState state){
		super(state, Graph.generateRandomGraph(15,60));
	}//Instead of 4 and 4, this is where the user input is added

	/*public void mouseDragged(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		clickedVertex = graph.getVertexAt(x, y);
		if(clickedVertex != -1){
			System.out.println("DRAG 2");
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
			if(newY > Game.HEIGHT-v.getDiameter()*1.4)
				newY = (int) (Game.HEIGHT-v.getDiameter()*1.4);
			graph.getVertex(clickedVertex).move(newX, newY);
		}
	}*/
	public void mousePressed(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		clickedVertex = graph.getVertexAt(x, y);
		if(clickedVertex != -1){
			// Enter here what needs to be done when mouse is pressed on a vertex
		}
	}
	public void mouseReleased(MouseEvent e){
		clickedVertex = -1;
	}

	public void keyPressed(KeyEvent e){
		super.keyPressed(e);
	}

	public void draw(Graphics g) {
		graph.draw(g);
	}

	public void tick() {}
}
