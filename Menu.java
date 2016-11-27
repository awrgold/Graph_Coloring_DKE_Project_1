import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Menu extends Level {    
    private Graph graph;
	public Menu(GameState state){
		super(state, null);
		Vertex[] items = new MenuVertex[2];
		items[0] = new MenuVertex(Game.WIDTH/4, Game.HEIGHT/2, " Import");		// TODO We need to find a way to make these points equidistant from the edges, same as the...
		items[1] = new MenuVertex(Game.WIDTH/2, Game.HEIGHT/2, "Generate");		// TODO ...pause menu issue, where the vertices are displayed evenly in the frame. (ratio issue when dividing double/fraction)
		graph = new Graph(items, new int[][]{ new int[]{1}, new int[0]});
		super.setGraph(graph);//this was necessary to make the MouseDragged method in the super class working
	}
    public void draw(Graphics2D g) {
		g.setFont(new Font("Main Menu Font", Font.BOLD, 20));
		graph.draw(g);
    }

    public void tick(){}

    public void mousePressed(MouseEvent e){
    	super.mousePressed(e);
		if(e.getButton() == MouseEvent.BUTTON1){
    			//insert here what the menu items have to do
    			//start a level
    		if(clickedVertex==0){
				String input = JOptionPane.showInputDialog("PLease give the directory to your graph file");
				String[] args = {input};
				state.states[GameState.INGAME] = new PlaygroundLevel(state, args); //Start the actual game, what happens if we cannot compute the chromatic number??
				state.setState(GameState.INGAME);
			}
			else if(clickedVertex == 1){
				int n; //number of vertices
				int m; //number of edges
    			do{
					String input = JOptionPane.showInputDialog("Please input the number of vertices and edges, seperated by a spaces.");
					String in[] = input.split(" ");
					n = Integer.parseInt(in[0]);
					m = Integer.parseInt(in[1]);
				}while(m<n-1||(m>((n*(n-1))/2))); //The #vertices and #edges were not compatible together (to less edges: always disconnected vertices, or to much edges: graph would be more than complete)
    			state.states[GameState.INGAME] = new PlaygroundLevel(state, n, m); //Start the actual game
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
