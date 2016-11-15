import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Menu extends Level {
	private int dir = 3;
    private MenuVertex[] items;
    private Edge[] edges;
    //the currently clicked vertex
    private int clickedVertex;
    //vertex-mouse-offset: the offset of the mouse from the center of the vertex (used so that the vertex doesn't "jump" when starting to drag
    private int vmOffsetX;
    private int vmOffsetY;
   // private Level[] gameStates;
    public Menu(GameState state){
    	super(state);
    //	this.gameStates = gameStates;
    	clickedVertex = -1;
    	items = new MenuVertex[2];
    	items[0] = new MenuVertex(Game.WIDTH/2, Game.HEIGHT/2,new int[]{1},"Option 0");
    	items[1] = new MenuVertex(Game.WIDTH/3*2, Game.HEIGHT/3*2, new int[]{1},"Option 1");
    	
    	edges = new Edge[]{new Edge(0,1)};
    }
    public void draw(Graphics g) {
        //draw the edges
        for(Edge e : edges){
        	//why do i need +Vertex.DIAMETER/2 (=15) ????????????????????????? works though
        	g.drawLine(items[e.u].getX()+15, items[e.u].getY()+15, items[e.v].getX()+15, items[e.v].getY()+15);
        }
        
    	//draw the vertices
       for(MenuVertex v : items){
    	   v.draw(g);
       }       
    }

    public void tick(){
/*    	if(items[0].getY()+dir > 0 && items[0].getY()+dir < Game.HEIGHT){
    		items[0].move(items[0].getX(), items[0].getY()+dir);
    	} else {
    		dir = -dir;
    	} */
    }

    public void mouseDragged(MouseEvent e){
    	System.out.println("DRAGGING");
    	if(clickedVertex != -1){
    		int newX = e.getX()-vmOffsetX;
    		int newY = e.getY()-vmOffsetY;
    		//some case handeling if the mouse is off screen (prevents the vertex from moving off-screen as well)
    		//WEIRD: one can move a vertex slightly closer to the border on the left-hand side than on the right-hand side.
    		if(newX < -items[clickedVertex].getDiameter()/2)
    			newX = -items[clickedVertex].getDiameter()/2;
    		if(newX > Game.WIDTH-items[clickedVertex].getDiameter()/2)
    			newX = Game.WIDTH-items[clickedVertex].getDiameter()/2;
    		if(newY < -items[clickedVertex].getDiameter()/2)
    			newY = -items[clickedVertex].getDiameter()/2;
    		//why 1.4? cuz i dunno, to prevent above mentioned weirdness.
    		if(newY > Game.HEIGHT-items[clickedVertex].getDiameter()*1.4)
    			newY = (int) (Game.HEIGHT-items[clickedVertex].getDiameter()*1.4);
    		items[clickedVertex].move(newX, newY);
    	}
    }
    public void mousePressed(MouseEvent e){
    	int x = e.getX();
    	int y = e.getY();
    	boolean found = false;
    	for(int i = 0;i<items.length && !found;i++){
    		if(items[i].contains(x, y)){
    			//is it a right click?
    			if(e.getButton() == MouseEvent.BUTTON3){
    				clickedVertex = i;
    			}
    			//is it a left click?
    			if(e.getButton() == MouseEvent.BUTTON1){
    				//insert whatever vertex i has to do
    				if(i == 1){
    					state.states[GameState.INGAME] = new PlaygroundLevel(state);
    					state.setState(GameState.INGAME);
    				}
    			} 
    			found = true;
    			vmOffsetX = x-items[i].getX();
    			vmOffsetY = y-items[i].getY();
    		}
    	}
    }
    
    public void mouseReleased(MouseEvent e){
    	clickedVertex = -1;
    }
    //if one presses esc, and there is an active but paused level, resume the level and set the menu to be not active
    public void keyPressed(KeyEvent e){
    	System.out.println("sth happened!");
    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
    		
    	}
    }
}
