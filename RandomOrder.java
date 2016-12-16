import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;

public class RandomOrder extends GameMode{
    private int currVertex;
    private int[] availableColorsForCurrVertex;
    private ArrayList<Integer> notColored;
    private BacktrackGreedy bg; //backtrack greedy for the hints

    public RandomOrder(GameState state, Graph graph){
        super(state,graph);
        addHUD(new RandomOrderHUD());
//        state.replaceState(new GameOverScreen(state), GameState.ENDGAME_SCREEN);
        notColored = new ArrayList<>(graph.getNumberOfVertices());
        setVertexListener(new VL());
        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            notColored.add(i, i);
        }
        bg = new BacktrackGreedy(graph.getAdjacencyMatrix());
        currVertex = selectNextRandomVertex();
        graph.getVertex(currVertex).highlight(true);
        //start backtrack greedy search in new thread, so that the game doesn't freeze for large graphs
        new Thread(() -> { // more Lambda <3
            bg.backtrackSearch();
            System.out.println("bg.maxColor = " + bg.maxColor);
        }).start();
        availableColorsForCurrVertex = graph.getAvailableColors(currVertex);
    }

    private int selectNextRandomVertex(){
        if(notColored.isEmpty())
            return -1;
        int index =(int) (Math.random()*notColored.size());
        int v = notColored.get(index);
        notColored.remove(index);
        return v;
    }

    private String showHint() {
        return "BLUB";
    }




    private class RandomOrderHUD extends HUD{
        public void draw(Graphics2D g){}
        public RandomOrderHUD(){
            JButton hintButton = new JButton("Gimme a HINT!");
            JLabel hintText = new JLabel();
            //LAMBDA <3
            hintButton.addActionListener(e -> hintText.setText(showHint()));
            add(hintButton);
            add(hintText);
        }
    }
    private class VL extends VertexListener{
        public void vertexPressed(int v, int mouseButton){
            if(v == currVertex){
                showColorSelectionMenu(v, availableColorsForCurrVertex);
            }
        }
        public void vertexHovered(int v){}
        public void vertexColored(int v) {
            getGraph().getVertex(currVertex).highlight(false);
            currVertex = selectNextRandomVertex();
            if (currVertex == -1) {
                getGameState().replaceState(makeGameOverScreen(), GameState.ENDGAME_SCREEN);
                getGameState().changeState(GameState.ENDGAME_SCREEN);
            } else {
                getGraph().getVertex(currVertex).highlight(true);
                availableColorsForCurrVertex = getGraph().getAvailableColors(v);
            }
        }
    }
}