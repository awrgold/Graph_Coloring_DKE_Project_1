import java.awt.image.BufferedImage;

public class PlaygroundMode extends GameMode{
    public PlaygroundMode(GameState gamestate){
        super(gamestate,GraphUtil.generateRandomGraph(10,10));
    }
}
