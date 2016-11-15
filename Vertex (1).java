import java.awt.*;

public class Vertex{

    private static int STANDARD_DIAMETER = 30;
    protected int color;
    protected int x;
    protected int y;
    protected int[] adjacentEdges;
    protected int diameter;
    public Vertex(int x, int y, int[] adjacentEdges){
        this.x = x;
        this.y = y;
        this.adjacentEdges = adjacentEdges;
        this.diameter = STANDARD_DIAMETER;
    }
    public int getDiameter(){
    	return diameter;
    }
    public void setDiameter(int diameter){
    	this.diameter = diameter;
    }
    public void draw(Graphics g){
    //    Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, diameter, diameter);
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
