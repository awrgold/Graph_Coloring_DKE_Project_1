import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import static constants.Drawing.*;

/**
 * Created by jurri on 6-12-2016.
 */
public class GameOverMenu extends Level {
    private static String result = "xxx";
	private AudioPlayer blip1;

    public GameOverMenu(GameState state) {
        super(state, null);
        MenuVertex[] items;
        clickedVertex = -1;
        items = new MenuVertex[1];
        items[0] = new MenuVertex(Game.WIDTH / 4, Game.HEIGHT / 4, "   Main Menu");
        graph = new Graph(items, new int[][]{new int[]{}});
        //Add result, depending on how the player peformed
		blip1 = new AudioPlayer("/resources/SFX/blip 1.wav");
        
    }

    public static void setResult(String x){
        System.out.println("Changed string");
		result = x;
    }
	
	
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (clickedVertex != -1){
				blip1.play();
				//elevMusic.stop();
			}if (clickedVertex == 0){
                state.setState(GameState.MAIN_MENU);
				//elevMusic.stop();
            }
			clickedVertex = -1; 
		}
		
    }
	
	@Override
    public void keyPressed(KeyEvent e){
    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			state.setState(GameState.MAIN_MENU);
			elevMusic.stop();
    	}
    }

    @Override
    public void draw(Graphics2D g) {
        g.setFont(new Font("Game Over Menu Font", Font.BOLD, 18));
        g.drawString(result, (Game.WIDTH/8), (Game.HEIGHT/2)-9);
        graph.draw(g);
    }
    @Override
    public void tick() {
        // TODO Auto-generated method stub

    }
}
