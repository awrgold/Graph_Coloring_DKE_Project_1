import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class ColorSelectionMenu {
    //the standard radius
    public final static int STANDARD_RADIUS = 60;
    public static final Color HIGHLIGHT_COLOR = Color.RED;
    private Vertex v;
    private int[] colors;
    // The radius of the circle whose edge contain the edges of the polygon which again contains the colors
    //by default it is the STANDARD_RADIUS, but if the vertex is somehow bigger, radius = v.getDiameter()/2
    private int radius;
    private int highlightedTile;
    private int currVertexColor;

    /**
     *
     * @param v
     * @param colors the colors which can be selected, it is assumed that colors[0] is a new color (a small plus gets drawn in the corresponding tile)
     */
    public ColorSelectionMenu(Vertex v, int currVertexColor, int[] colors){
        this.v = v;
        this.colors = colors;
        this.currVertexColor = currVertexColor;
        highlightedTile = -1;
        if(v.getDiameter() > 2*STANDARD_RADIUS){
            radius = v.getDiameter()/2;
        } else {
            radius = STANDARD_RADIUS;
        }
    }

    /**
     * draws the color selection menu
     * The ColorSelectionMenu is drawn centered around the vertex v and is a regular polygon with a small circle in the middle.
     * The polygon splits up into colors.length triangles, each being drawn in a different color and each representing a element of colors[].
     * The circle in the middle is drawn in the current color of the vertex and has a small border.
     * @param g a Graphics object for drawing (surprise surprise)
     */
    //TODO: fix drawing for 2 colors
    public void draw(Graphics2D g) { //TODO: add a small plus sign to tile 0 (which is assumed to be a introducable color)
        if(colors.length > 2) {
            //h stands for highlighted
            int hx1 = v.getCX();
            int hx2 = hx1;
            int hy1 = v.getCY();
            int hy2 = hy1;
            for (int i = 1; i <= colors.length; i++) {
                int x1 = (int) Math.round(v.getCX() + (radius) * Math.cos((i - 1) * 2 * Math.PI / colors.length));
                int y1 = (int) Math.round(v.getCY() + (radius) * Math.sin((i - 1) * 2 * Math.PI / colors.length + Math.PI));
                int x2 = (int) Math.round(v.getCX() + (radius) * Math.cos(i * 2 * Math.PI / colors.length));
                int y2 = (int) Math.round(v.getCY() + (radius) * Math.sin(i * 2 * Math.PI / colors.length + Math.PI));
                fillTile(g, Graph.COLORS[colors[i - 1]], x1, y1, x2, y2);
                if (i - 1 == highlightedTile) {
                    hx1 = x1;
                    hx2 = x2;
                    hy1 = y1;
                    hy2 = y2;
                }
            }
            g.setStroke(new BasicStroke(3));
            drawTile(g, HIGHLIGHT_COLOR, hx1, hy1, hx2, hy2);
            g.setStroke(new BasicStroke(1));
            g.setColor(Graph.COLORS[currVertexColor]);
            g.fill(new Ellipse2D.Double(v.getCX() - 10, v.getCY() - 10, 20, 20));
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2));
            g.draw(new Ellipse2D.Double(v.getCX() - 10, v.getCY() - 10, 20, 20));
            g.setStroke(new BasicStroke(1));
        } else if(colors.length == 2){
            g.setColor(Graph.COLORS[colors[0]]);
            g.fillPolygon(new int[]{v.getCX()-STANDARD_RADIUS,v.getCX(),v.getCX()+STANDARD_RADIUS},new int[]{v.getCY(),v.getCY()-STANDARD_RADIUS,v.getCY()},3);
            g.setColor(Graph.COLORS[colors[1]]);
            g.fillPolygon(new int[]{v.getCX()-STANDARD_RADIUS,v.getCX(),v.getCX()+STANDARD_RADIUS},new int[]{v.getCY(),v.getCY()+STANDARD_RADIUS,v.getCY()},3);
            g.setStroke(new BasicStroke(3));
            g.setColor(HIGHLIGHT_COLOR);
            if(highlightedTile == 0){
                g.drawPolygon(new int[]{v.getCX()-STANDARD_RADIUS,v.getCX(),v.getCX()+STANDARD_RADIUS},new int[]{v.getCY(),v.getCY()-STANDARD_RADIUS,v.getCY()},3);
            } else {
                g.drawPolygon(new int[]{v.getCX()-STANDARD_RADIUS,v.getCX(),v.getCX()+STANDARD_RADIUS},new int[]{v.getCY(),v.getCY()+STANDARD_RADIUS,v.getCY()},3);
            }
            g.setStroke(new BasicStroke(1));
        } else if(colors.length == 1){
            g.setColor(HIGHLIGHT_COLOR);
            g.fill(new Ellipse2D.Double(v.getCX() - STANDARD_RADIUS/2, v.getCY() - STANDARD_RADIUS/2, STANDARD_RADIUS, STANDARD_RADIUS));
            g.setColor(Graph.COLORS[colors[0]]);
            System.out.println("colors[0] = " + colors[0]);
            g.fill(new Ellipse2D.Double(v.getCX() - STANDARD_RADIUS/2+5,v.getCY() - STANDARD_RADIUS/2+5,STANDARD_RADIUS-10,STANDARD_RADIUS-10));
        }
    }

    /**
     * Calculates the selected tile for a given point, which is the tile intersecting the line from the vertex center to the given point
     * @param x x-coordinate
     * @param y y-coordinate
     * @return the selected color (which is a element of colors[], which has been passed on to the constructor)
     */
    public int getSelection(int x, int y){
        return colors[getSelectedTile(x,y)];
    }

    /**
     * highlights the to the given point corresponding tile, until highlight is called again
     * the corresponding tile is the tile intersecting the line from the vertex center to the given point
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void highlight(int x, int y){

        highlightedTile = getSelectedTile(x,y);
    }

    private int getSelectedTile(int x, int y){
        double angle = Math.atan2(x - v.getCX(), y - v.getCY()) + 3*Math.PI/2;

        if (angle >= Math.PI*2) angle -= Math.PI*2.0;
        if (angle < 0)          angle += 2*Math.PI;

        return (int)( (angle / (2*Math.PI)) * colors.length);
    }

    private void fillTile(Graphics2D g, Color c, int x1, int y1, int x2, int y2){
        g.setColor(c);
        g.fillPolygon(new int[]{v.getCX(), x1, x2}, new int[]{v.getCY(), y1, y2}, 3);
    }

    private void drawTile(Graphics2D g, Color c, int x1, int y1, int x2, int y2){
        g.setColor(c);
        g.drawPolygon(new int[]{v.getCX(), x1, x2}, new int[]{v.getCY(), y1, y2}, 3);
    }
}
