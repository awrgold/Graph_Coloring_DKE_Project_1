import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javafx.scene.shape.Ellipse;

/**
* Information about the vertices is stored in here - this method is initially made by Jonas
* @param x represents the x coordinate of the vertex
* @param y represents the y coordinate of the vertex
* @param adjacentEdges contains all edges that are connected to this vertex (at least their index in the edge[] array)
*/
public class Vertex{

	private static final int STANDARD_DIAMETER = 30;
	protected int color;
	protected int x;
	protected int y;
	protected int diameter;
	public void highlight(boolean highlight){}
	public Vertex(int x, int y){
		this.x = x;
		this.y = y;
		this.diameter = STANDARD_DIAMETER;
	}

	public int getDiameter(){
		return diameter;
	}

	public void setDiameter(int diameter){
		this.diameter = diameter;
	}

	public void draw(Graphics2D g){
	    Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.ORANGE);
		g2.fill(new Ellipse2D.Double(x,y,diameter,diameter));
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
		return (px-x)*(px-x)+(py-y)*(py-y) <= diameter*diameter;
	}
}