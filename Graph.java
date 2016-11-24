import java.awt.Graphics;
import java.util.Arrays;

/**
 * V1.2 Created by Jurriaan Berger edited by Jonas
 * Random graph generator debugged by Jurriaan
 * An object of this class contains the basic information about the graph and its features, i.e. edges and vertices.
 * Please keep following in mind: labelling of edges and vertices starts at 0!!!
 * This class is able to: draw graph, whether an x-y coordinate is inside a vertex,
 */

public class Graph{
	//some explanation for that variable: it's gonna be n long, and contains in every row all neighbouring vertices with a higher index, the last one would thus have no entries
	//also there cannot be a row containing 0, and most importantly, when looping through it, every edge occurs exactly once
	private int[][] neighbours;
	private Vertex vertices[];
	/**
	 * Initialises a graph with given vertices and edges
	 * @param vertices
	 * @param neighbours neighbours.length == vertices.length, every row contains the index of adjacent vertices with a higher index
	 *  for more info look above
	 */
	public Graph(Vertex[] vertices, int[][] neighbours){
		this.vertices = vertices;
		this.neighbours = neighbours;
	}
	/**
	 * draws the graph's vertices and edges
	 * @param g a graphics object from a image/canvas to draw on
	 */
	public void draw(Graphics g){
		//drawing the edges
		//one could add a if-statement so that the edges change color if any adjacent vertex is highlighted
		//also some kind of style information could be passed on to the constructor and used here
		//drawing the vertices
		for(Vertex v : vertices){
			v.draw(g);
		}
		for(int i = 0;i<neighbours.length;i++)
			for(int neighbour : neighbours[i])
				g.drawLine(vertices[i].getX()+vertices[i].getDiameter()/2, vertices[i].getY()+vertices[i].getDiameter()/2, vertices[neighbour].getX()+vertices[neighbour].getDiameter()/2, vertices[neighbour].getY()+vertices[neighbour].getDiameter()/2);

	}
	/**
	 *
	 * @param x-coordinate of a point on the game canvas
	 * @param y-coordinate of a point on the game canvas
	 * @return -1 if no vertex could be found, otherwise the vertex's index is returned
	 */
	public int getVertexAt(int x, int y){
		for(int i = 0;i<vertices.length;i++){
			if(vertices[i].contains(x,y))
				return i;
		}
		return -1;
	}
	/**
	 *
	 * @param v the index of a vertex (must be >= 0 and <n)
	 * @return returns the vertex
	 */
	public Vertex getVertex(int v){
		return vertices[v];
	}

	/**
	 * Constructs a graph, that's loaded from a file
	 */
	//public static Graph readGraphFromFile(){

	//return new Graph
	//}

	/** DEBUG ME, I NEVER GOT ACTUALLY TESTED, HELP ME D:
	 * Generates a random but connected graph:
	 * @param n is the number of vertices, n>=2
	 * @param m is the number of edges, m is expected to be n-1<=m<=(n-1)*n*0.5 (the graph has to be at least connected and cannot contain more edges than a complete graph)
	 * @return Graph ....
	 */
	public static Graph generateRandomGraph(int n, int m){
		//these arrays are going to be filled with the vertices and edges of the graph
		Vertex[] vertices = new Vertex[n];
		int[][] neighbours = new int[n][m];
		for(int i = 0; i<n; i++){
			int x = (int)  (Math.random()*((double) Game.WIDTH));
			int y = (int)  (Math.random()*((double) Game.HEIGHT));
			vertices[i] = new Vertex(x,y);
			for(int j=0; j<m; j++){
				neighbours [i][j] = -1; //This is necessary because a vertex can actually be adjacent to vertex 0 -> things go very very wrong when default value would be set to 0
			}
		}

		//assigning neighbours to vertices for m times (== #edges)
		for(int i = 0; i<m; i++){
			//first, generate a path through the graph
			if(i<n-1) neighbours[i][0] = i+1;//Actually the next vertex -> i+1
				//now randomly connected vertices that weren't connected before, until the desired number of edges is reached
			else {
				//randomly select two vertices to create a edge between them
				int u = (int) (Math.random() * n);//COUNTING
				int v = (int) (Math.random() * n);//COUNTING
				//check for u==v
				while (u == v) {
					v = (int) (Math.random() * n); //the random assigned vertex to v has to differ from u COUNTING
				}
				//Check if the created edge already existed
				boolean exists = true;
				for(int j=0; j<i; j++){
					if((neighbours[u][j]==v)||(neighbours[v][j]==u)){ //COUNTING
						exists = false;
						System.out.println("FALSE "+u+" "+v);
						i--; //Generate random vertices for edge[i] again
						break;
					}
				}

				if(exists){
					int min = Math.min(u, v);
					int max = Math.max(u, v);

					// Store the new adjacent vertex at the right position
					for (int j = 0; j < n; j++) {
						if (neighbours[min][j] == -1) {
							neighbours[min][j] = max;
							break;
						}
					}
				}


			}
		}

		//now cut off the zeros (=unused entries) in every row of neighbours COUNTING
		for(int i = 0;i<n;i++){
			for(int k = 0;k<m;k++){
				if(neighbours[i][k] == -1){
					int[] temp = neighbours[i];
					neighbours[i] = new int[k];
					System.arraycopy(temp, 0, neighbours[i], 0, k);
					break;
				}
			}
		}
		for(int i = 0; i<n;i++){
			System.out.println("Vertex: " +i+ " adjacent to: " +Arrays.toString(neighbours[i]));
		}

		return new Graph(vertices, neighbours);
	}

	public static void main (String[]args){
		generateRandomGraph(3,3);
	}
}