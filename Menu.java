import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Menu extends Level {
//	private int dir = 3;
    private MenuVertex[] items;
    private Graph graph;
    //vertex-mouse-offset: the offset of the mouse from the center of the vertex (used so that the vertex doesn't "jump" when starting to drag
    private int vmOffsetX;
    private int vmOffsetY;
    /*public Menu(GameState state){
    	super(state, null);
    //	this.gameStates = gameStates;
    	clickedVertex = -1;
    	items = new MenuVertex[2];
    	items[0] = new MenuVertex(Game.WIDTH/2, Game.HEIGHT/2,"Option 0");
    	items[1] = new MenuVertex(Game.WIDTH/3*2, Game.HEIGHT/3*2, "Option 1");
    	graph = new Graph(items, new int[][]{ new int[]{1}, new int[0]});
    }*/
	public Menu(GameState state){
		super(state, null);
		//	this.gameStates = gameStates;
		items = new MenuVertex[2];
		items[0] = new MenuVertex(Game.WIDTH/4, Game.HEIGHT/2, " Import");		// TODO We need to find a way to make these points equidistant from the edges, same as the...
		items[1] = new MenuVertex(Game.WIDTH/2, Game.HEIGHT/2, "Generate");		// TODO ...pause menu issue, where the vertices are displayed evenly in the frame. (ratio issue when dividing double/fraction)
		graph = new Graph(items, new int[][]{ new int[]{1}, new int[0]});
		super.setGraph(graph);//this was necessary to make the MouseDragged method in the super class working
	}
    public void draw(Graphics g) {
		g.setFont(new Font("Main Menu Font", Font.BOLD, 20));
		graph.draw(g);
    }

    public void tick(){}

    public void mousePressed(MouseEvent e){
    	super.mousePressed(e);
		if(e.getButton() == MouseEvent.BUTTON1){
    			//insert here what the menu items have to do
    			//start a level
    			if(clickedVertex == 1){
    				state.states[GameState.INGAME] = new PlaygroundLevel(state);
    				state.setState(GameState.INGAME);
    			}
    			clickedVertex = -1;
    	}
    }
    
    public void keyPressed(KeyEvent e){
    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
    		
    	}
    }
}
