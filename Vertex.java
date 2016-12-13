import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

//fix the meaning of x,y, adjust that programwide (in means of optics)
//also note that the contains() method has to be adjusted
/**
* Information about the vertices is stored in here - this method is initially made by Jonas
*  x represents the x coordinate of the vertex (upper left corner of the square containing the vertex on screen)
*  y represents the y coordinate of the vertex (upper left corner of the square containing the vertex on screen)
*/
public class Vertex {
	private static final Color STANDARD_COLOR = Color.WHITE;
	private static final Color STANDARD_HIGHLIGHT_BORDER_COLOR = Color.RED;
	private static final Color STANDARD_BORDER_COLOR = Color.BLACK;
	protected static final int STANDARD_DIAMETER = 30;
	protected int x;
	protected int y;
	protected int diameter;
	private boolean isHighlighted;

	public Vertex(int x, int y){
		this.x = x;
		this.y = y;
		this.diameter = STANDARD_DIAMETER;
	}
	public void highlight(boolean highlight){ //setter
		this.isHighlighted = highlight;
	}

	public boolean isHighlighted(){
		return isHighlighted;
	}

	public int getDiameter(){
		return diameter;
	}

	public void setDiameter(int diameter){
		this.diameter = diameter;
	}

	public Color getStandardColor(){
		return STANDARD_COLOR;
	}
	
	public void draw(Graphics2D g, Color c){
		if(isHighlighted){
			g.setColor(STANDARD_HIGHLIGHT_BORDER_COLOR);
		} else {
			g.setColor(STANDARD_BORDER_COLOR);
		}
		//draw a border around the inner circle, red or black, if highlighted or not, respectively
		g.fill(new Ellipse2D.Double(x, y, diameter, diameter));

		g.setColor(c);
		g.fill(new Ellipse2D.Double(x+((double)diameter)/12,y+((double)diameter/12),((double)diameter*5)/6,((double)diameter*5)/6));

	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	/**
	 *
	 * @return the x-coordinate of the center
	 */
	public int getCX() {
		return x + diameter / 2;
	}
	/**
	 *
	 * @return the y-coordinate of the center
	 */
	public int getCY() {
		return y + diameter / 2;
	}

	/**
	 * moves the upper left corner of the square containing the vertex to the given point
	 * @param x new x coordinate
	 * @param y new y coordinate
	 */
	public void move(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	* 
	* @param px x-coordinate of a point
	* @param py y-coordinate of a point
	* @return true, if the given point is inside the circle of the on-screen representation of the vertex, false otherwise
	* 
	*/
	public boolean contains(int px, int py){
		//make the given point relative to the center
		int dx = px - getCX();
		int dy = py - getCY();
		//is the euclidean distance less then the radius? (= is the point in the circle?)
		return dx*dx+dy*dy <= (diameter*diameter)/4;
	}
}