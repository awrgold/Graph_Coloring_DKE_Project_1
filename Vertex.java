import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javafx.scene.shape.Ellipse;
//fix the meaning of x,y, adjust that programwide (in means of optics)
//also note that the contains() method has to be adjusted
/**
* Information about the vertices is stored in here - this method is initially made by Jonas
* @param x represents the x coordinate of the vertex (upper left corner of the square containing the vertex on screen)
* @param y represents the y coordinate of the vertex (upper left corner of the square containing the vertex on screen)
*/
public class Vertex{
	private static final Color STANDARD_COLOR = Color.WHITE;
	private static final int STANDARD_DIAMETER = 30;
	protected Color color;
	protected int x;
	protected int y;
	protected int diameter;
	public void highlight(boolean highlight){}
	public Vertex(int x, int y){
		this.x = x;
		this.y = y;
		this.diameter = STANDARD_DIAMETER;
		this.color = STANDARD_COLOR;
	}

	public int getDiameter(){
		return diameter;
	}

	public void setDiameter(int diameter){
		this.diameter = diameter;
	}

	public void draw(Graphics2D g){
		g.setColor(Color.BLACK);
		g.fill(new Ellipse2D.Double(x, y, diameter, diameter));
		g.setColor(color);
		g.fill(new Ellipse2D.Double(x+((double)diameter)/12,y+((double)diameter/12),((double)diameter*5)/6,((double)diameter*5)/6));
	}
	public void setColor(Color color){
		this.color = color;
	}
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public void move(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	* 
	* @param px x-coordinate of a point
	* @param py y-coordinate of a point
	* @return true, if the point is inside the circle of the on-screen representation of the vertex, false otherwise
	* 
	*/
	public boolean contains(int px, int py){
		//make the given point relative to the center
		int dx = px-x-diameter/2;
		int dy = py-y-diameter/2;
		//is the euclidian distance less then the radius? (= is the point in the circle?)
		return dx*dx+dy*dy <= (diameter*diameter)/4;
	}
}