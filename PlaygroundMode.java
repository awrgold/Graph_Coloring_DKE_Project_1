import java.awt.image.BufferedImage;

public class PlaygroundMode extends GameMode{
	private int mode;
	
    public PlaygroundMode(GameState state, Graph graph, int mode){
        super(state,graph);
		super.setVertexListener(new PlaygroundVertexListener());
		this.mode = mode;
    }
	
	class PlaygroundVertexListener extends VertexListener{//Is not working
	    public void vertexReleased(int v, int mouseButton) {
			System.out.println("Action Performed");
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
						System.out.println("GAME ENDED");
						GameOverMenu.setResult("You found the optimal coloring");
					}else{
						GameOverMenu.setResult("You finished and used: "+(50)+" colors. "+50+" colors would have been sufficient either");
						GameOverMenu.setResult("You finished and used: "+(usedColors-1)+" colors. "+chromNR+" colors would have been sufficient either");
					}
					state.changeState(GameState.ENDGAME_SCREEN); //In case of mode 3,
				}
				}
            }
        }
	}
}
