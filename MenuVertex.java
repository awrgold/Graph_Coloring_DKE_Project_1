import java.awt.*;
import java.awt.Graphics;

public class MenuVertex extends Vertex {
	public static final int DIAMETER = 120;
	private String label;
	public MenuVertex(int x, int y, String label) {
		super(x, y);
		this.label = label;
		setDiameter(DIAMETER);

	}
	public void draw(Graphics2D g){
        //g.drawOval(x,y,diameter,diameter);
        super.draw(g);
		g.setColor(Color.BLACK);
		g.drawString(label, this.getCX(), this.getCY());
	}

}