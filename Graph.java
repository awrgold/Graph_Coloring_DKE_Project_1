import java.awt.Graphics2D;
import java.util.Arrays;
import java.awt.Color;
import java.io.*;
import java.util.*;

/**
 * V1.2 Created by Jurriaan Berger edited by Jonas
 * Random graph generator debugged by Jurriaan
 * An object of this class contains the basic information about the graph and its features, i.e. edges and vertices.
 * Please keep following in mind: labelling of edges and vertices starts at 0!!!
 */

public class Graph{
	private static Color STANDARD_EDGE_COLOR = Color.BLACK;
	//some explanation for that variable: it's gonna be n long, and contains in every row all neighbouring vertices with a higher index, the last one would thus have no entries
	//also there cannot be a row containing 0, and most importantly, when looping through it, every edge occurs exactly once
	private int[][] neighbours;
	private Vertex vertices[];
	
	private Color edgeColor;
	//nope
	private static final boolean DEBUG = true;
	public final static String COMMENT = "//";
	private static int chromaticNumber;
	/**
	 * Initialises a graph with given vertices and edges
	 * @param vertices
	 * @param neighbours neighbours.length == vertices.length, every row contains the index of adjacent vertices with a higher index
	 *  for more info look above
	 */
	public Graph(Vertex[] vertices, int[][] neighbours){
		this.vertices = vertices;
		this.neighbours = neighbours;
		this.edgeColor = STANDARD_EDGE_COLOR;
	}
	/**
	 * draws the graph's vertices and edges
	 * @param g a Graphics2D object from a image/canvas to draw on
	 */
	public void draw(Graphics2D g){
		//drawing the edges
		//one could add a if-statement so that the edges change color if any adjacent vertex is highlighted
		//also some kind of style information could be passed on to the constructor and used here
		//drawing the vertices
		g.setColor(edgeColor);
		for(int i = 0;i<neighbours.length;i++)
			for(int neighbour : neighbours[i])
				g.drawLine(vertices[i].getX()+vertices[i].getDiameter()/2, vertices[i].getY()+vertices[i].getDiameter()/2, vertices[neighbour].getX()+vertices[neighbour].getDiameter()/2, vertices[neighbour].getY()+vertices[neighbour].getDiameter()/2);
	
		for(Vertex v : vertices){
			v.draw(g);
		}
	}
	/**
	 *
	 * @param x-coordinate of a point on the game canvas
	 * @param y-coordinate of a point on the game canvas
	 * @return -1 if no vertex could be found, otherwise the vertex's index is returned
	 */
	public int getVertexAt(int x, int y){
		for(int i = 0;i<vertices.length;i++)
			if(vertices[i].contains(x,y))
				return i;
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
	 * Reads-in the graph from a file, we make use of the code provided to us in the beginning of the project.
	 * @param file for example: Graph09.txt (It's required that this graph is placed in the same folder, (we might need to change this)
	 * @return Graph...
	 */
	public static Graph readGraphFromFile(String file[]){
		if( file.length < 1 )
		{
			System.out.println("Error! No filename specified.");
			System.exit(0);
		}
		String inputfile = file[0];
		boolean seen[] = null;

		int n = -1; //number of vertices
		int m = -1; //number of edges

		try 	{
			FileReader fr = new FileReader(inputfile);
			BufferedReader br = new BufferedReader(fr);

			String record = new String();

			//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
			//! These comments are only allowed at the top of the file.

			//! -----------------------------------------
			while ((record = br.readLine()) != null)
			{
				if( record.startsWith("//") ) continue;
				break; // Saw a line that did not start with a comment -- time to start reading the data in!
			}

			if( record.startsWith("VERTICES = ") )
			{
				n = Integer.parseInt( record.substring(11) );
				if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
			}
			Vertex[] vertices = new Vertex[n];
			for(int i = 0; i<n; i++){
				int x = (int)  (Math.random()*((double) Game.WIDTH));
				int y = (int)  (Math.random()*((double) Game.HEIGHT));
				vertices[i] = new Vertex(x,y);
			}

			seen = new boolean[n+1];
			record = br.readLine();

			if( record.startsWith("EDGES = ") )
			{
				m = Integer.parseInt( record.substring(8) );
				if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
			}
			int[][] neighbours = new int[n][m];

			for( int d=0; d<m; d++)
			{
				if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
				record = br.readLine();
				String data[] = record.split(" ");
				if( data.length != 2 )
				{
					System.out.println("Error! Malformed edge line: "+record);
					System.exit(0);
				}

				int u = Integer.parseInt(data[0])-1; // Reading the first vertex
				int v = Integer.parseInt(data[1])-1; // Reading the second vertex

				// Store the new adjacent vertex at the right position - right now we do not check whether an edge already existed
				int min = Math.min(u, v);
				int max = Math.max(u, v);
				System.out.println("max: "+max+". min: "+min);
				for (int j = 0; j < n; j++) {
					if (neighbours[min][j] == 0) {
						neighbours[min][j] = max;
						break;
					}
				}
				//seen[ e[d].u ] = true;
				//seen[ e[d].v ] = true;

				if(DEBUG) System.out.println(COMMENT + " Edge: "+ u +" "+v);

			}

			String surplus = br.readLine();
			if( surplus != null )
			{
				if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");
			}

			//now cut off the zeros (=unused entries) in every row of neighbours COUNTING
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
			if(DEBUG){
				for(int i = 0; i<n;i++){
					System.out.println("Vertex: " +i+ " adjacent to: " +Arrays.toString(neighbours[i]));
				}
			}

			//Check if we can compute the chromatic number
			chromaticNumber = computableRandomGraph(n, m, neighbours);
			//if(chromaticNumber>=0) {//At this moment, when we have five recursive calls, this method will be called five times -> not very clean????
			//	if(DEBUG)System.out.println("We have found the chromatic number");
				return new Graph(vertices, neighbours);
			//}else{
			//	if(DEBUG){System.out.println("We have not found the chromatic number, so please load a new graph.");}//How will the user know this
			//	return null;
			//}
		}
		catch (IOException ex)
		{
			// catch possible io errors from readLine()
			System.out.println("Error! Problem reading file "+inputfile);
			System.exit(0);
		}

		for( int x=1; x<=n; x++ )
		{
			if( seen[x] == false )
			{
				if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
			}
		}
		return null;
	}

	/**
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
		}
		//make a path through all vertices, so that the graph is connected
		for(int i = 0; i<n-1;i++){
			neighbours[i][0] = i+1;
		}
		//generate m-(n-1) (=the edges left over after the path) edges randomly
		for(int i = 0; i<m-n+1; i++){
			//randomly select two vertices to create a edge between them
			int u = (int) (Math.random() * n);
			int v = (int) (Math.random() * n);
			//check for u==v
			while (u == v) {
				v = (int) (Math.random() * n); //the random assigned vertex to v has to differ from u COUNTING
			}
			int min = Math.min(u, v);
			int max = Math.max(u, v);
			//Check if the created edge already existed
			boolean exists = false;
			for(int j=0; j<neighbours[min].length; j++){
				if((neighbours[min][j]==max)){ //COUNTING
					exists = true;
					if(DEBUG){System.out.println("FALSE "+u+" "+v);}
					i--; //Generate random vertices for edge[i] again
					break;
				}
			}
			if(!exists){
				// Store the new adjacent vertex at the right position
				for (int j = 0; j < n; j++) {
					if (neighbours[min][j] == 0) {
						neighbours[min][j] = max;
						break;
					}
				}
			}
		}
		//now cut off the zeros (=unused entries) in every row of neighbours COUNTING
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
		if(DEBUG){
			for(int i = 0; i<n;i++){
				System.out.println("Vertex: " +i+ " adjacent to: " +Arrays.toString(neighbours[i]));
			}
		}
		return new Graph(vertices, neighbours);
	}

	/**V 1.0 Method created by Jurriaan Berger
	 * Makes use of the algorithms that we wrote in the previous phase, however they are/need to bee adjusted to the int[][] that we have right now
	 * Probably store the chromatic number somewhere -> HOW
	 * @param n number of vertices
	 * @param m number of edges
	 * @param adjList
	 * @return the chromatic number, if we couldn't compute it, we return 0.
	 */
	public static int computableRandomGraph(int n, int m, int [][] adjList){
		//Creation of the adjacency matrix
		int[][] adjMatrix = makeAdjMatrix(n,adjList);
		if(DEBUG){
			for(int[] row : adjMatrix)
				System.out.println(Arrays.toString(row));
		}

		//Checking whether the graph is either complete
/*		if ( ( ( n*(n-1) ) / 2) == m ) {
			if(DEBUG){System.out.println("Chromatic number = "+n);}
			return n;
		}

		//Checking whether the graph is bipartite
		if (is2colorable(adjMatrix) )
		{
			if(DEBUG){System.out.println("Chromatic number = 2");}
			return 2;
		}
*/
		//Greedy, back-track, welsh-powell and maximum-clique algorithms to figure out the chromatic number
		MaximumClique cliqueFinder = new MaximumClique(adjMatrix);
		//Greedy greedy = new Greedy(adjList);  //Adjecency list is not yet in the same format!!!
		BacktrackGreedy backtrack = new BacktrackGreedy(adjMatrix);
		int maxCliqueSize = cliqueFinder.maxCliqueSize;
		System.out.println ("Maximum Clique gives (Lower Bound): "+maxCliqueSize);
		int welshPowellOutput = WelshPowell.getchromaticnr(adjMatrix);
		System.out.println ("Welsh Powell gives: "+welshPowellOutput);
		//int greedyOutput = greedy.chromaticNumber;
		//if(maxCliqueSize == Math.min(welshPowellOutput, greedyOutput)){
		int backtrackOutput = backtrack.maxColor;
		System.out.println ("Bakctrack Greedy gives: "+backtrackOutput);
		if(maxCliqueSize == welshPowellOutput){
			if(DEBUG){System.out.println("Chromatic number = "+ maxCliqueSize);}
			return maxCliqueSize;
		} else{
			return -1;
		}
	}

	/**This method makes the adjacency matrix, in the same format as we had it in the previous phase, therefore we can immediately start using our algorithms
	 * @param n the number of vertices
	 * @param adjList needs the matrix with neighbours of all vertices
	 * @return
	 */
	private static int[][] makeAdjMatrix(int n, int[][] adjList) {
		int[][] adjMatrix = new int[n][n];
		for (int i = 0; i < adjList.length; i++) {
			for (int j = 0; j <adjList[i].length; j++){
				adjMatrix[i][adjList[i][j]]=1;
				adjMatrix[adjList[i][j]][i]=1;
			}
		}
		return adjMatrix;
	}

	private static boolean is2colorable(int[][] adjMatrix) {
		int[] colorArr = new int[adjMatrix.length];
		for (int i = 0; i < adjMatrix.length; i++)
		{
			colorArr[i] = -1;
		}
		colorArr[0] = 1;
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.add(0);

		while (q.size() != 0)
		{
			int u = q.poll();

			for (int v = 0; v < adjMatrix.length; v++)
			{
				if (adjMatrix[u][v]==1 && colorArr[v]==-1)
				{
					colorArr[v] = 1-colorArr[u];
					q.add(v);
				}
				else if (adjMatrix[u][v]==1 && colorArr[u]==colorArr[v]) {
					return false;
				}
			}
		}
		return true;
	}
	public static void main(String[] args){
		Graph g = generateRandomGraph(4,6);
		computableRandomGraph(4,6,g.neighbours);
		Game game = new Game();
		game.gamestate.states[GameState.INGAME] = new PlaygroundLevel(game.gamestate,g);
	}
}