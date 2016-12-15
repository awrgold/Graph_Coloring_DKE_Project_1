import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RandomOrder extends GameMode{
    private int currVertex;
    private ArrayList<Integer> notColored;
    public RandomOrder(GameState state, Graph graph){
        super(state,graph);
        super.addHUD(new RandomOrderHUD());
        state.replaceState(new GameOverScreen(state), GameState.ENDGAME_SCREEN);
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
    private GameOverScreen makeGameOverScreen(){
        return new GameOverScreen(getGameState());
    }
    private String showHint(){
        return "BLUB";
    }
    private class RandomOrderHUD extends HUD{
        public void draw(Graphics2D g){}
        public RandomOrderHUD(){
            JButton hintButton = new JButton("Gimme a HINT!");
            JLabel hintText = new JLabel();

            hintButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hintText.setText(showHint());
                }
            });
            add(hintButton);
            add(hintText);
        }
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
                getGameState().replaceState(makeGameOverScreen(), GameState.ENDGAME_SCREEN);
                getGameState().changeState(GameState.ENDGAME_SCREEN);
            } else getGraph().getVertex(currVertex).highlight(true);
        }
    }
}