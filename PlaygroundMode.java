import java.awt.image.BufferedImage;

public class PlaygroundMode extends GameMode{
    public PlaygroundMode(GameState gamestate){
        super(gamestate,GraphUtil.generateRandomGraph(10,10));
		super.setVertexListener(new PlaygroundVertexListener());
    }
	public class PlaygroundVertexListener extends VertexListener{
	    public void vertexReleased(int v, int mouseButton) {
            if (mouseButton == 1) {
                GameState state = getGameState();
				if(graph.fullyColored()){
				System.out.println("Mode "+mode+" finished");
				int usedColors = graph.getUsedColors(); //Doesn't work
				System.out.println("used "+ usedColors+" colors");
				int chromNR = graph.getChromaticNR();
				System.out.println("chromatic number: "+chromNR);
				if(mode == 1){//In case of mode 1
					if(usedColors-1==chromNR){ // The -1 is obviously not correct
						GameOverMenu.setResult("SUCCESS");
						System.out.println("Success");
						state.changeState(GameState.ENDGAME_SCREEN);
					}
				}else if(mode == 3){
					if(usedColors-1==chromNR){
						GameOverMenu.setResult("You found the optimal coloring");
					}else{
						GameOverMenu.setResult("You finished and used: "+(usedColors-1)+" colors. \n"+chromNR+" colors would have been sufficient either");
					}
					state.changeState(GameState.ENDGAME_SCREEN); //In case of mode 3,
				}
				}
            }
        }
	}
}
