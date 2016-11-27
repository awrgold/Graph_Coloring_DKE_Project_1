package CircleSolver;
import java.lang.Math;

public class CircleSolver {   
    
    //this method takes as parameters the x and y coordinates of the anchor point (top left, not the center of the circle), the radius, and number of vertices
    //if "inner" is set to true, all vertices will be offset by half a position clockwise
    //at this stage, it can be called with e.g. 20 nodes, and then again with 10 inner nodes and a reduced radius, to find 30 positions on 2 concentric circles such that no 2 positions fall on a line through the center
    //finding a method to take the edge list into account and place the nodes accordingly is quite a bit harder; I've been trying some things but no luck so far
    //it returns int values for the positions because if I do the calculations in double precision, the truncation errors add up to MASSIVE errors in the final positions
    //in a large space (radius values of over 500 or so), the int precision this gives should be okay
    //if it looks shit and we need higher precision, we could multiply the radius and all coordinates by 10 (or 100, or 1000) before running it, and then put a decimal point in the results where appropriate - that is way more precise and reliable than running the whole thing with doubles and accepting the compounding truncation errors
    //running it with doubles, I had cases where positions where "off" from where they should be by as much as a fifth of the radius
    
    public static void main (String[] args) {
        int[][] outer = circlesolver (-600, 600, 600, 15, false);
            for (int i = 0; i < 15; i++) {
                System.out.println("Point " + (i+1) + " :     " + outer[i][0] + ",     " + outer[i][1]);
            }
        int[][] inner = circlesolver (-400, 400, 400, 15, true);
            for (int i = 0; i < 15; i++) {
                System.out.println("Point " + (i+1) + " :     " + inner[i][0] + ",     " + inner[i][1]);
            }
    }
    
    
    public static int[][] circlesolver (double anchorx, double anchory, double radius, int n, boolean inner) {
        
        double theta = ((2 * Math.PI) / n);
        int[][] points = new int[n][2];
        double cx = anchorx + radius;
        double cy = anchory - radius;
        for (int i = 0; i < n; i++) {
            double angle = theta * i;
            points[i][0] = (int) (cx + (radius * (Math.cos(angle))));
            points[i][1] = (int) (cy + (radius * (Math.sin(angle))));
        }
        if (inner) {
            int temp1 = points[0][0];
            int temp2 = points[0][1];
            for (int i = 0; i < n-1; i++) {
                points[i][0] = (points[i][0] + points[i+1][0]) / 2;
                points[i][1] = (points[i][1] + points[i+1][1]) / 2;
            }
            points[n-1][0] = (points[n-1][0] + temp1) / 2;
            points[n-1][1] = (points[n-1][1] + temp2) / 2;
        }
        return points;
    }  
}
