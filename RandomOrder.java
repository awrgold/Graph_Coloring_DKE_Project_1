import java.util.ArrayList;

/**
 * Created by Tschei on 15/12/2016.
 */
public class RandomOrder extends GameMode{
    private int currVertex;
    private ArrayList<Integer> notColored;
    public RandomOrder(GameState state, Graph graph){
        super(state,graph);
        notColored = new ArrayList<>(graph.getNumberOfVertices());
        setVertexListener(new VL());
        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            notColored.add(i, i);
        }
        currVertex = selectNextRandomVertex();
        graph.getVertex(currVertex).highlight(true);
    }
    private int selectNextRandomVertex(){
        if(notColored.isEmpty())
            return -1;
        int index =(int) (Math.random()*notColored.size());
        int v = notColored.get(index);
        notColored.remove(index);
        return v;
    }
    private class VL extends VertexListener{
        public void vertexPressed(int v, int mouseButton){
            if(v == currVertex){
                super.vertexPressed(v,mouseButton);
            }
        }
        public void vertexHovered(int v){}
        public void vertexColored(int v){
            getGraph().getVertex(currVertex).highlight(false);
            currVertex = selectNextRandomVertex();
            if(currVertex == -1){
                //GAME OVEEER, insert that here
            } else getGraph().getVertex(currVertex).highlight(true);
        }
    }
}
