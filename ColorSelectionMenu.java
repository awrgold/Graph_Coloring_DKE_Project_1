import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

// TODO: 12/9/2016 Cleanup
public class ColorSelectionMenu {
    //the standard radius
    public final static int STANDARD_RADIUS = 60;
    public static final Color HIGHLIGHT_COLOR = Color.RED;
    public final static Color[] COLORS = new Color[]{Color.BLUE, Color.PINK, new Color(0x9B30FF), Color.CYAN, Color.GREEN, Color.darkGray, Color.LIGHT_GRAY};
    private Vertex v;
    private int[] colors;
    // The radius of the circle whose edge contain the edges of the polygon which again contains the colors
    //by default it is the STANDARD_RADIUS, but if the vertex is somehow bigger, radius = v.getDiameter()/2
    private int radius;
    private int highlightedTile;
    public ColorSelectionMenu(Vertex v, int[] colors){
        this.v = v;
        this.colors = colors;
        highlightedTile = -1;
        if(v.getDiameter() > 2*STANDARD_RADIUS){
            radius = v.getDiameter()/2;
        } else {
            radius = STANDARD_RADIUS;
        }
    }

    public void draw(Graphics2D g) {
        //h stands for highlighted
        int hx1 = v.getCX();
        int hx2 = hx1;
        int hy1 = v.getCY();
        int hy2 = hy1;
        for (int i = 1; i <= colors.length; i++) {
            int x1 = (int) Math.round(v.getCX() + (radius) * Math.cos((i-1) * 2*Math.PI / colors.length));
            int y1 = (int) Math.round(v.getCY() + (radius) * Math.sin((i-1) * 2*Math.PI / colors.length + Math.PI));
            int x2 = (int) Math.round(v.getCX() + (radius) * Math.cos(  i   * 2*Math.PI / colors.length));
            int y2 = (int) Math.round(v.getCY() + (radius) * Math.sin(  i   * 2*Math.PI / colors.length + Math.PI));
            fillTile(g, COLORS[colors[i-1]], x1, y1, x2, y2);
            if(i-1 == highlightedTile){
                hx1 = x1;
                hx2 = x2;
                hy1 = y1;
                hy2 = y2;
            }
        }
        g.setStroke(new BasicStroke(3));
        drawTile(g,HIGHLIGHT_COLOR,hx1,hy1,hx2,hy2);
        g.setStroke(new BasicStroke(1));
        g.setColor(v.getColor());
        g.fill(new Ellipse2D.Double(v.getCX()-10,v.getCY()-10,20,20));
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.draw(new Ellipse2D.Double(v.getCX()-10,v.getCY()-10,20,20));
        g.setStroke(new BasicStroke(1));
    }

    public Color getSelection(int x, int y){

        return COLORS[colors[getSelectedTile(x,y)]];
    }
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
