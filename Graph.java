import java.awt.*;
import java.util.*;


/**
* V1.0 Created by Jurriaan Berger 
* An object of this class contains the basic information about the graph and its features, i.e. edges and vertices.
* This class is able to: draw graph, whether an x-y coordinate is inside a vertex,
*/

public class Graph{
	public Edge e[];
	public Vertex x[];
	private static final boolean DEBUG = true;
	
	/**
	* Constructs a random graph, and thereby objects of the class Edge for all edges and objects of the class Vertex for all vertices
	* @param n is the number of vertices
	* @param m is the number of edges
	* @return Graph ....
	*/

	public static Graph generateRandomGraph(int n, int m){
		
		Vertex x[] = new Vertex[n]; //Creating the objects that represents the edges.
		Edge e[] = new Edge[m]; //Creating the objects that represents the edges.
		
		
		//Creating edges
		for(int i=0; i<m; i++){ //using for loop to assign all the edges

			if(i<n-1){ //First, create at least a path trough the whole graph -> prevent multiple loose graphs and prevent unconnected vertices
				e[i] = new Edge (i+1, i+2);
			}else if(i==n-1){ //The last one of this path is the first vertex connect to the last one
				e[i] = new Edge (i+1, 1);
			}else{
				int u = 1+(int)(Math.random()*n); //A random assigned vertex
				int v = 1+(int)(Math.random()*n); //A random assigned vertex 
			
				while(u==v){
					v = 1+(int)(Math.random()*n); //the random assigned vertex to v has to differ from u
				}
				
				e[i] = new Edge (u,v);
			}
			
			for(int j=0; j<i; j++){
				if((e[j].u==e[i].u||e[j].u==e[i].v)&&(e[j].v==e[i].v||e[j].v==e[i].u)){ //If the created edge already existed
					i--; //Generate random vertecis for edge[i] again
					if(DEBUG) System.out.println("//" + j +" - "+ i + " False Edge: "+ e[i].u +" "+e[i].v);
				}	
			}
		}
		
		if(DEBUG){
			for(int i=0; i<m;i++){
				System.out.println("//" + " Edge: " + i + " = " + e[i].u +" "+e[i].v);
			}
		}
		
		
		
		//Creating vertices
		
		for(int i=0;i<n; i++){
			int a=i; //x coordinate
			int b=i; //y coordinate
			
			
			int [] adjList = new int[number of connected edges];												//NOT YET DONE: How to create the adjList with the connected edges
			
			x[i]  = new Vertex (a, b, adjList);	
		}
		 
		
		
		
		
		
		return new Graph();
	}
	
	/**
	* Constructs a graph, that's loaded from a file
	*/
	//public static Graph readGraphFromFile(){
		
		//return new Graph
	//}
	
	/**
	* Information about the edge is stored in here - this method is initially made by Jonas
	* @param u represents the first connected vertex
	* @param v represents the other connected vertex
	*/
	
	static class Edge{
		int u;
		int v;
		boolean isHighlited;
		public Edge(int u, int v){
			this.u = u;
			this.v = v;
		}
	}

	/**
	* Information about the vertices is stored in here - this method is initially made by Jonas
	* @param x represents the x coordinate of the vertex
	* @param y represents the y coordinate of the vertex
	* @param adjacentEdges is an array that contains all edges that are connected to this vertex
	*/
	static class Vertex{

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
}