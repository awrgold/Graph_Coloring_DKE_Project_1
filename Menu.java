import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Menu extends GameMode {

	private Font font;
	public Menu(GameState state){
		super(state, getMenuGraph());

		font = new Font("Main Menu Font", Font.BOLD, 20);
		super.setVertexListener(new MenuVertexListener());
		//load the background image and set it
        try {
            setBackgroundImage(ImageIO.read(new File("rsc/img/BackgroundGrid1.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g) {
		g.setFont(font);
		super.draw(g);
    }

    private class MenuVertexListener extends VertexListener{
	    private GameState state = getGameState();
	    public void vertexPressed(int v, int mouseButton) {
            if (mouseButton == 1) {
                if (v == 0) {
                    JFileChooser fileChooser = new JFileChooser("C:\\Users\\antonwnk\\Project 1\\phase1\\Test set");
                    int hasFile = fileChooser.showDialog(null, "Choose graph file");
                    if (hasFile == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        int mode = getModeFromDialog();
                        System.out.println("mode is: " + mode);
                        state.replaceState(new PlaygroundMode(state, GraphUtil.readGraphFromFile(file), mode), GameState.INGAME); //Start the actual game. HOW to implement feedback: what happens if we cannot compute the chromatic number, or the file was corrupt??
                        state.changeState(GameState.INGAME);
                    }
//				String input = JOptionPane.showInputDialog("Please give the directory to your graph file, make use of \\"+"\\ instead of \\. ");//needs to be fixed
//				String[] args = {input};
                } else if (v == 1) {
                    Boolean falseVertexEdgeComb = true;
                    int n = 0;
                    int m = 0;
                    while (falseVertexEdgeComb) {
                        JTextField vertexField = new JTextField(5);
                        JTextField edgeField = new JTextField(5);

                        JPanel myPanel = new JPanel();
                        myPanel.add(new JLabel("vertices:"));
                        myPanel.add(vertexField);
                        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                        myPanel.add(new JLabel("edges:"));
                        myPanel.add(edgeField);

                        int result = JOptionPane.showConfirmDialog(null, myPanel,
                                "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            m = Integer.parseInt(vertexField.getText());//number of vertices
                            n = Integer.parseInt(edgeField.getText());//number of edges
                            //The #vertices and #edges were/were not compatible together (to less edges: always disconnected vertices, or to much edges: graph would be more than complete)
                            if ((n < m - 1) || (n > ((m * (m - 1)) / 2))) { //They were not > Throw an error message
                                JPanel newPanel = new JPanel();
                                JOptionPane.showMessageDialog(newPanel, "Problem with creating a graph, with: " + m + "vertices, and: " + n + " edges.", "Backup problem", JOptionPane.ERROR_MESSAGE);
                            } else { //They were > create the graph
                                int mode = getModeFromDialog();
                                System.out.println("mode is: " + mode);
                                falseVertexEdgeComb = false;
                                state.replaceState(new PlaygroundMode(state, GraphUtil.generateRandomGraph(m, n), mode), GameState.INGAME); //Start the actual game
                                state.changeState(GameState.INGAME);
                            }
                        } else if (result == JOptionPane.CANCEL_OPTION) {
                            falseVertexEdgeComb = false;
                        }
                    }
                } else if (v == 2) {
                    state.replaceState(new GameMode(state, new Graph(CircleSolver.getVertexObjects(CircleSolver.circlesolver(170, 0, 270, 14, false)), new int[][]{})), GameState.INGAME); //Here we test out CircleSolver
                    state.changeState(GameState.INGAME);
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(0);
    	}
    }

    private int getModeFromDialog(){
    	//String[] buttons = {"one","two","three"};
		int mode = 0;
    	JRadioButton oneButton = new JRadioButton ("The bitter end.");
		JRadioButton twoButton = new JRadioButton ("The best upper bound, in a fixed time.");
		JRadioButton threeButton = new JRadioButton ("Random order.");
		oneButton.setSelected(true);
		ButtonGroup group = new ButtonGroup ();
		group.add(oneButton);
		group.add(twoButton);
		group.add(threeButton);
		JPanel panel = new JPanel();
		panel.add(oneButton);
    	panel.add(twoButton);
    	panel.add(threeButton);

		JOptionPane.showOptionDialog(null, panel,"Radio Test", JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (oneButton.isSelected()) mode =1;
		else if (twoButton.isSelected()) mode = 2;
		else if (threeButton.isSelected()) mode = 3;
		return mode;
	}
	//sigh... ugly workaround... SWIIING SWIIIIIIING, where are you? Probably there, where time is.
    //would have done that in JavaFX, but unfortunately Smirnov said no :(
	private static Graph getMenuGraph(){
        Vertex[] items = new MenuVertex[2];
        items[0] = new MenuVertex((Game.WIDTH / 4) - MenuVertex.DIAMETER / 2, ((int) Game.HEIGHT * 3 / 4) - MenuVertex.DIAMETER / 2, "Import");
        items[1] = new MenuVertex((Game.WIDTH * 3 / 4) - MenuVertex.DIAMETER / 2, (Game.HEIGHT * 3 / 4) - MenuVertex.DIAMETER / 2, "Generate");
        return new Graph(items, new int[][]{new int[]{1},new int[]{}});
    }
}
