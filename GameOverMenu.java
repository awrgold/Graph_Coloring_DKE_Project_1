import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by jurri on 6-12-2016.
 */
public class GameOverMenu extends Level {
    private String result = "xxx";

    public GameOverMenu(GameState state) {
        super(state, null);
        MenuVertex[] items;
        clickedVertex = -1;
        items = new MenuVertex[3];
        items[0] = new MenuVertex(Game.WIDTH / 4, Game.HEIGHT / 2, "   Main Menu");
        items[1] = new MenuVertex(Game.WIDTH*3 / 4, Game.HEIGHT / 2, "   Try Again");
        items[2] = new MenuVertex(Game.WIDTH/2, Game.HEIGHT/4,result);
        graph = new Graph(items, new int[][]{new int[]{1,2},new int[]{2},new int[]{}});
        //Add result, depending on how the player peformed
        //JLabel resultString = new JLabel ("You succeeded");
        /*items = new MenuVertex[4];
        items[0] = new MenuVertex(Game.WIDTH/4, Game.HEIGHT/2, "   Save");		// Spaces in string(s) to offset the text to be perfectly above the vertex.
        items[1] = new MenuVertex((Game.WIDTH*2)/3, Game.HEIGHT/2, "   Quit");
        items[2] = new MenuVertex((Game.WIDTH*2)/3, Game.HEIGHT/4, " Restart");
        items[3] = new MenuVertex(Game.WIDTH/4, Game.HEIGHT/4, " Resume");
        graph = new Graph(items, new int[][]{new int[]{1,3}, new int[]{2,3}, new int[]{3}, new int[]{}});*/
    }

    public void setResult(String x){
        result = x;
    }
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (clickedVertex == 0){
                state.setState(GameState.MAIN_MENU);
            }else if(clickedVertex == 1){
                Graph inGameGraph = PlaygroundLevel.getPlayGraph(); //Grab the graph the user played with. (only the position of the vertices is not correct right now)
                inGameGraph.decolorGraph(); //Reset the colors of this graph
                state.setState(GameState.INGAME); //Return to the playgroundlevel
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setFont(new Font("Pause Menu Font", Font.BOLD, 24));
        g.drawString("GAME OVER", (Game.WIDTH/2)-25, (Game.HEIGHT/2)-15);
        graph.draw(g);
    }
    @Override
    public void tick() {
        // TODO Auto-generated method stub

    }
}
