import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class GameMode extends State implements Runnable{

	private ColorSelectionMenu csm;
	private Graph graph;
	private int clickedVertex;
	private int lastHoveredVertex;
	private boolean isDragging;
	//vertex-mouse-offset: the offset of the mouse from the center of the vertex (used so that the vertex doesn't "jump" when starting to drag
	private int vmOffsetX;
	private int vmOffsetY;
	private Canvas graphDisplay;
	private Image bgImg; //background image
	private boolean running;
	private Thread thread; //the thread for the game loop
	private VertexListener vl;
	private HUD hud;
	public GameMode(GameState state, Graph graph){
		super(state);
		clickedVertex = -1;
		lastHoveredVertex = 0;
		isDragging = false;
		this.graph = graph;
		running = false;
		graphDisplay = new Canvas();
		graphDisplay.setFocusable(true);
		graphDisplay.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		setLayout(new BorderLayout());
		add(graphDisplay, BorderLayout.CENTER);
		graphDisplay.addKeyListener(this);
		graphDisplay.addMouseMotionListener(this);
		graphDisplay.addMouseListener(this);
		vl = new VertexListener();
	}
	public void addHUD(HUD hud){
		this.hud = hud;
		add(hud,BorderLayout.SOUTH);
	}

	public void setBackgroundImage(Image bgImg){
		this.bgImg = bgImg;
	}
	public void setVertexListener(VertexListener vl){ this.vl = vl;}
	public GameState getGameState(){return gamestate;}
	public Graph getGraph(){return graph;}
	protected void tick(){}
	private void render() {
		BufferStrategy bs = graphDisplay.getBufferStrategy();
		if(bs == null){
			graphDisplay.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		draw(g);
		bs.show();
		g.dispose();
	}

	public void draw(Graphics2D g){
		g.setColor(Color.WHITE);
		if(bgImg != null)
			g.drawImage(bgImg, 0, 0, this);
		else
			g.fillRect(0,0,getWidth(),getHeight());
		graph.draw(g);
		if(csm != null) {
			csm.draw(g);
		}
		if(hud != null) hud.draw(g);
	}
	private synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		try {
			running = false;
			thread.join();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void enter(){
		start();
	}
	public void exit(){
		stop();
	}

	/**
	 * shows the ColorSelection menu on top of v, with colors[]. The not recommended colors get a thick black frame.
	 * @param v the vertex on which the CSM appears
	 * @param colors the colors which the CSM depicts
	 * @param notRecommended the colors which are not recommended, e.g. notRecommended[0] == false
	 *                       if colors[0] isn't recommended (used for the hint option)
	 */
	public void showCSM(int v, int[] colors, boolean[] notRecommended){

	}

	public void showColorSelectionMenu(int v, int[] colors){
		csm = new ColorSelectionMenu(graph.getVertex(v),graph.getVertexColor(v),colors);
	}

	public void run(){
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		long timer2 = System.currentTimeMillis();
		int frames = 0;
		//cap to ~60 fps, by forcing a minimum time between frames
		long minTimeBetweenFrames =(long) 17; //in ms
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			now = System.currentTimeMillis();
			if (running && now - timer > minTimeBetweenFrames) {
				timer += minTimeBetweenFrames;
				render();
				frames++;
			} else {
				try {
					Thread.sleep(minTimeBetweenFrames - (now - timer));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(System.currentTimeMillis() - timer2 > 1000){
				timer2 += 1000;
				//System.out.println("FPS: "+frames);
				frames = 0;
			}
		}
	}

	//from here on just event handling
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			gamestate.changeState(GameState.PAUSE_MENU);
	}
	public void mouseMoved(MouseEvent e){
		int currHoveredVertex = graph.getVertexAt(e.getX(), e.getY());
		if(currHoveredVertex != lastHoveredVertex) {
			vl.vertexHovered(currHoveredVertex);
		}
		lastHoveredVertex = currHoveredVertex;
	}

	public void mousePressed(MouseEvent e){
		clickedVertex = graph.getVertexAt(e.getX(), e.getY());
		if(clickedVertex != -1){
			Vertex v = graph.getVertex(clickedVertex);
			vmOffsetX = e.getX()-v.getX();
			vmOffsetY = e.getY()-v.getY();
			if(e.getButton() == MouseEvent.BUTTON3){
				isDragging = true;
			}
			vl.vertexPressed(clickedVertex, e.getButton());
		}
	}

	public void mouseReleased(MouseEvent e){
		if(clickedVertex != -1 && e.getButton() == MouseEvent.BUTTON1 && csm != null){
			graph.setVertexColor(clickedVertex, csm.getSelection(e.getX(),e.getY()));
			vl.vertexColored(clickedVertex);
			csm = null;
		}
		clickedVertex = -1;
		if(e.getButton() == MouseEvent.BUTTON3){
			isDragging = false;
		}
	}

	public void mouseDragged(MouseEvent e){
		if(clickedVertex != -1 && isDragging){
			int newX = e.getX() - vmOffsetX;
			int newY = e.getY() - vmOffsetY;
			Vertex v = graph.getVertex(clickedVertex);
			if(newX < 0)
				newX = 0;
			if(newX > Game.WIDTH - v.getDiameter())
				newX = Game.WIDTH - v.getDiameter();
			if(newY < 0)
				newY = 0;
			if(newY > Game.HEIGHT - v.getDiameter())
				newY = Game.HEIGHT - v.getDiameter();

			v.move(newX, newY);
		}
		if(csm != null){
			csm.highlight(e.getX(),e.getY());
		}
	}
	public class VertexListener{
		/**
		 * Gets invoked whenever the hovered vertex changes, by default this vertex gets then highlighted, while the former highlighted vertex is lowlighted.
		 * @param v is the newly hovered vertex, v>=0 if an actual vertex, -1 if a vertex is just exited
		 */
		public void vertexHovered(int v){
			if(v != -1) graph.getVertex(v).highlight(true);
			if(lastHoveredVertex != -1) graph.getVertex(lastHoveredVertex).highlight(false);
		}

		/**
		 * Gets invoked whenever a mouse button is pressed on top of a vertex, by default a ColorSelectionMenu is shown.
		 * @param v the pressed vertex, v>= 0
		 * @param mouseButton the used mouse button, which is either 1,2 or 3
		 */
		public void vertexPressed(int v, int mouseButton){
		    if(mouseButton == 1) showColorSelectionMenu(v,graph.getAvailableColors(v));
		}

		/**
		 * Gets invoked whenever a mouse button is released on top of a vertex, and has been pressed on the same vertex
		 * @param v the vertex
		 * @param mouseButton the released mouse button, which is either 1,2 or 3
		 */
		public void vertexReleased(int v, int mouseButton){}

		/**
		 * Is called whenever a vertex is moved
		 * @param v the moved vertex
		 */
		public void vertexMoved(int v){}

		/**
		 * Is called whenever a vertex is colored
		 * @param v the colored vertex
		 */
		public void vertexColored(int v){}
	}
	public abstract class HUD extends JPanel {
	    /**
		 * Draws the elements of the HUD, which are visible on the graphDisplay canvas
		 * @param g a Graphics2D object from the graphDisplay canvas
		 */
		public abstract void draw(Graphics2D g);
	}
}
