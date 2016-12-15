import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameOverMenu extends GameMode {
	private static String result = "xxx";
	
	public GameOverMenu(GameState state) {
		super(state,getGameOverMenuGraph());
		MenuVertex[] items;
		super.setVertexListener(new PauseMenuVertexListener());
	}
	
	public class PauseMenuVertexListener extends VertexListener{
	    public void vertexPressed(int v, int mouseButton) {
            if (mouseButton == 1) {
                GameState state = getGameState();
                if (v == 0) { // If they click "Quit," send them back to the start menu.
                    state.changeState(GameState.MAIN_MENU);
                }
            }
        }
	}
	
	public static void setResult(String x){
        System.out.println("Changed string");
		result = x;
    }
	
	@Override
	public void draw(Graphics2D g) {
		g.setFont(new Font("Pause Menu Font", Font.BOLD, 24));
		g.drawString("PAUSED", (Game.WIDTH/2)-25, (Game.HEIGHT/2)-15);
		super.draw(g);
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			getGameState().changeState(GameState.MAIN_MENU);
	}
	
	private static Graph getGameOverMenuGraph(){
        MenuVertex[] items = new MenuVertex[1];
        items[0] = new MenuVertex(Game.WIDTH / 4, Game.HEIGHT / 4, "   Main Menu");
        return new Graph(items, new int[][]{new int[]{}});
    }
}