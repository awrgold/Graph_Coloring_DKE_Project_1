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
	public GameMode(GameState state, Graph graph){
		super(state);
		clickedVertex = -1;
		lastHoveredVertex = 0;
		isDragging = false;
		this.graph = graph;
		running = false;
		graphDisplay = new Canvas();
		setLayout(new BorderLayout());
		add(graphDisplay, BorderLayout.CENTER);
		graphDisplay.addKeyListener(this);
		graphDisplay.addMouseMotionListener(this);
		graphDisplay.addMouseListener(this);
	}
	public void setBackgroundImage(Image bgImg){
		this.bgImg = bgImg;
	}
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
	}
	private synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	private synchronized void stop() {
		try {
			thread.join();
			running = false;
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
		if(currHoveredVertex != lastHoveredVertex)
			graph.getVertex(lastHoveredVertex).highlight(false);
		if(currHoveredVertex != -1){
			Vertex v = graph.getVertex(currHoveredVertex);
			v.highlight(true);
			lastHoveredVertex = currHoveredVertex;
		}
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
		}
		if(clickedVertex != -1 && e.getButton() == MouseEvent.BUTTON1){
			csm = new ColorSelectionMenu(graph.getVertex(clickedVertex),graph.getVertexColor(clickedVertex),graph.getAvailableColors(clickedVertex));
		}
	}

	public void mouseReleased(MouseEvent e){
		if(clickedVertex != -1 && e.getButton() == MouseEvent.BUTTON1){
			Vertex v = graph.getVertex(clickedVertex);
			graph.setVertexColor(clickedVertex, csm.getSelection(e.getX(),e.getY()));
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
}
