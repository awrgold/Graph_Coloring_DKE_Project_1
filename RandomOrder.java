import java.util.ArrayList;

public class RandomOrder extends GameMode{
    private int currVertex;
    private int[] avaibleColorsForCurrVertex;
    private ArrayList<Integer> notColored;
    BacktrackGreedy bg; //backtrack greedy for the hints

    public RandomOrder(GameState state, Graph graph){
        super(state,graph);
        super.addHUD(new RandomOrderHUD());
        notColored = new ArrayList<>(graph.getNumberOfVertices());
        setVertexListener(new VL());
        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            notColored.add(i, i);
        }
        bg = new BacktrackGreedy(graph.getAdjacencyMatrix());
        setVertexListener(new VL());
        permutation = generateVertexPermutation();
        currIndex = 0;
        graph.getVertex(permutation[currIndex]).highlight(true);
        //start backtrack greedy search in new thread, so that the game doesn't freeze for large graphs
        new Thread(() -> { // more Lambda <3

        long start = System.currentTimeMillis();
        bg.backtrackSearch();
        System.out.println("bg.maxColor = " + bg.maxColor);
        System.out.println("GREEDY done after "+(start-System.currentTimeMillis()));
    }).start();
        avaibleColorsForCurrVertex = graph.getAvailableColors(permutation[currIndex]);
    }

    private int[] generateVertexPermutation(){
        ArrayList<Integer> notColored;
        int[] permutation = new int[getGraph().getNumberOfVertices()];
        notColored = new ArrayList<>(getGraph().getNumberOfVertices());

        for (int i = 0; i < getGraph().getNumberOfVertices(); i++) {
            notColored.add(i, i);
        }
        for (int i = 0; i < permutation.length; i++) {
            int index = (int) (Math.random() * notColored.size());
            permutation[i] = notColored.get(index);
            notColored.remove(index);
        }
        return permutation;
    }

    private GameOverScreen makeGameOverScreen(){
        return new GameOverScreen(getGameState(), "blubba", "booMBoom");
    }

    private String showHint(){
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
                showColorSelectionMenu(v, avaibleColorsForCurrVertex);
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
                getGraph().getVertex(permutation[currIndex]).highlight(true);
                avaibleColorsForCurrVertex = getGraph().getAvailableColors(permutation[currIndex]);
            }
        }
    }
}