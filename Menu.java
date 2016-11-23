import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Menu extends Level {
//	private int dir = 3;
    private MenuVertex[] items;
    private Graph graph;
    //the currently clicked vertex
    private int clickedVertex;
    //vertex-mouse-offset: the offset of the mouse from the center of the vertex (used so that the vertex doesn't "jump" when starting to drag
    private int vmOffsetX;
    private int vmOffsetY;
    public Menu(GameState state){
    	super(state, null);
    //	this.gameStates = gameStates;
    	clickedVertex = -1;
    	items = new MenuVertex[2];
    	items[0] = new MenuVertex(Game.WIDTH/2, Game.HEIGHT/2,"Option 0");
    	items[1] = new MenuVertex(Game.WIDTH/3*2, Game.HEIGHT/3*2, "Option 1");
    	graph = new Graph(items, new int[][]{ new int[]{1}, new int[0]});
    }
    public void draw(Graphics g) {
        /*//draw the edges
        for(Edge e : edges){
        	//why do i need +Vertex.DIAMETER/2 (=15) ????????????????????????? works though
        	g.drawLine(items[e.u].getX()+15, items[e.u].getY()+15, items[e.v].getX()+15, items[e.v].getY()+15);
        }
        
    	//draw the vertices
       for(MenuVertex v : items){
    	   v.draw(g);
       }  */
    	graph.draw(g);
    }

    public void tick(){
/*    	if(items[0].getY()+dir > 0 && items[0].getY()+dir < Game.HEIGHT){
    		items[0].move(items[0].getX(), items[0].getY()+dir);
    	} else {
    		dir = -dir;
    	} */
    }
    public void mouseDragged(MouseEvent e){

    	if(clickedVertex != -1){
    		System.out.println("DRAGGING THA SHIAT 2");
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
    }
    public void mousePressed(MouseEvent e){
    	int x = e.getX();
    	int y = e.getY();
    	clickedVertex = graph.getVertexAt(x, y);
    	if(clickedVertex != -1){
    		if(e.getButton() == MouseEvent.BUTTON3){
	    		System.out.println("BUTTON 3!");
	    		Vertex v = graph.getVertex(clickedVertex);
	    		vmOffsetX = x-v.getX();
	     		vmOffsetY = y-v.getY();
	   		} else if(e.getButton() == MouseEvent.BUTTON1){
    			//insert here what the menu items have to do
    			//start a level
    			if(clickedVertex == 1){
    				state.states[GameState.INGAME] = new PlaygroundLevel(state);
    				state.setState(GameState.INGAME);
    			}
    			clickedVertex = -1;
    		}
    	}
    }
    
    public void mouseReleased(MouseEvent e){
    	clickedVertex = -1;
    }
    public void keyPressed(KeyEvent e){
    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
    		
    	}
    }
}
