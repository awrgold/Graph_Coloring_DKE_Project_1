import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 900;
    public static final int HEIGHT = 600;

    private Thread thread;
    private boolean running = false;
    public GameState gamestate;

    //level 0 is the menu
    //level 1, needs to be added
    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        new Window("BeatTheKELK Build 0.2.2, IntelliJ rules", this);
        gamestate = new GameState();
    	gamestate.game = this;
        gamestate.states[0] = new Menu(gamestate);
        gamestate.states[2] = new PauseMenu(gamestate);
        GameInputListener il = new GameInputListener();
        this.addMouseListener(il);
        this.addMouseMotionListener(il);
        this.addKeyListener(il);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run() {                                                         
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
                System.out.println("FPS: "+frames);
                frames = 0;
            }
        }
        stop();
    }

    public void tick() {
    	gamestate.getActiveLevel().tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics bg = bs.getDrawGraphics();
        BufferedImage dbi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) dbi.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH,HEIGHT);
        gamestate.getActiveLevel().draw(g);

        bg.drawImage(dbi, 0, 0, this);
        bs.show();
        bg.dispose();

    }

    public static void main(String[] args) {
        new Game();
    }
    //listens to events and passes them to the active level, show up the menu on pressing escape
    private class GameInputListener implements KeyListener, MouseListener, MouseMotionListener{

        public void mouseClicked(MouseEvent e){
            gamestate.getActiveLevel().mouseClicked(e);
        }

        public void mousePressed(MouseEvent e){
            gamestate.getActiveLevel().mousePressed(e);
        }

        public void mouseReleased(MouseEvent e){
            gamestate.getActiveLevel().mouseReleased(e);
        }

        public void mouseDragged(MouseEvent e){gamestate.getActiveLevel().mouseDragged(e);}

        public void mouseMoved(MouseEvent e){
            gamestate.getActiveLevel().mouseMoved(e);
        }

    	public void mouseEntered(MouseEvent e){
			gamestate.getActiveLevel().mouseEntered(e);
    	}

        public void mouseExited(MouseEvent e) {
            gamestate.getActiveLevel().mouseExited(e);
        }

		@Override
		public void keyTyped(KeyEvent e) {
			gamestate.getActiveLevel().keyTyped(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			gamestate.getActiveLevel().keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			gamestate.getActiveLevel().keyReleased(e);
		}

    }
}
