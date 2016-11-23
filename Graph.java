import java.awt.Graphics;


/**
* V1.0 Created by Jurriaan Berger 
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
				g.drawLine(vertices[i].getX(), vertices[i].getY(), vertices[neighbour].getX(), vertices[neighbour].getY());
		
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
	* Generates a random but connected graph
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
		}
		//first, generate a path through the graph
		for(int i = 1; i<n;i++){
			neighbours[i-1][0] = i;
		}
		
		//now randomly connected vertices that weren't connected before, until the desired number of edges is reached
		//note: n-1 edges were used to create the path, so loop until m-(n-1)
		for(int i=0; i<m-n+1; i++){
			//randomly select two vertices to create a edge between them
			int u = (int)(Math.random()*n);
			int v = (int)(Math.random()*n);
			//check for u==v
			while(u==v){
				v = (int)(Math.random()*n); //the random assigned vertex to v has to differ from u
			}
			boolean isSafeToAdd = true;
			//check whether u and v are already connected
			int min = Math.min(u, v);
			int max = Math.max(u, v);
			for(int j = 0; j<i;j++){
				for(int k = 0;k<n-1;k++){
					if(neighbours[min][k] == max){
						isSafeToAdd = false;
						i--;
						break;
					} else if(k>=1 && neighbours[min][k] == 0)
						break;
				}
			}
			if(isSafeToAdd){
				//add to the first not used entry (as mentioned above the declaration of neighbours, 0 cannot occur naturally)
				for(int j = 0;j<n;j++){
					if(neighbours[min][j] == 0){
						neighbours[min][j] = max;
						break;
					}
				}
			}
		}
		//now cut off the zeros (=unused entries) in every row of neighbours
		for(int i = 0;i<n;i++){
			for(int k = 0;k<m;k++){
				if(neighbours[i][k] == 0){
					int[] temp = neighbours[i];
					neighbours[i] = new int[k];
					System.arraycopy(temp, 0, neighbours[i], 0, k);
					break;
				}
			}
		}
		return new Graph(vertices, neighbours);
	}
}