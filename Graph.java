import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * An object of this class contains the basic information about the graph and its features, i.e. edges and vertices.
 * labelling of edges and vertices starts at 0
 */

public class Graph{
	public final static Color[] COLORS = new Color[]{Color.WHITE, Color.BLUE, Color.PINK, new Color(0x9B30FF), Color.CYAN, Color.GREEN, Color.darkGray, Color.LIGHT_GRAY};
	private static final Color STANDARD_EDGE_COLOR = Color.BLACK;
	private static final Color STANDARD_EDGE_HIGHLIGHT_COLOR = Color.RED;

    //some explanation for the variable neighbours: it's gonna be n long, and contains in every row all neighbouring vertices with a higher index,
	//the last one would thus have no entries
	//also there cannot be a row containing 0, and most importantly, when looping through it, every edge occurs exactly once
	//the following block is set by standard in the constructor
	private int[][] neighbours; // TODO: 12/9/2016 make to neighbours
	private Vertex[] vertices;
	private Color edgeColor;
	private Color edgeHighlightColor;
	private int[][] adjacencyMatrix;
	//0 is not colored
	private int[] coloring;
	private int usedColors;
	private int coloredVertices; //number of colored vertices
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
		this.edgeHighlightColor = STANDARD_EDGE_HIGHLIGHT_COLOR;
		adjacencyMatrix = getAdjacencyMatrix();
		coloring = new int[neighbours.length];
		usedColors = 1;
		coloredVertices = 0;
	}
	public int getNumberOfVertices(){
		return vertices.length;
	}
	/**
	 * Computes the for a proper coloring available colors for a given vertex
	 * @param vertex the index of a vertex of this graph
	 * @return possible colors for the vertex, including a new color which is positioned at index 0
	 */
	public int[] getAvailableColors(int vertex){
		int[] availableColors = new int[usedColors];
		availableColors[0] = usedColors;
		int count = 1;
		//significantly optimizable
		for (int i = 1; i < usedColors; i++) {
			boolean isUsedByNeighbour = false;
			for (int j = 0; j < adjacencyMatrix.length; j++) {
				if(adjacencyMatrix[vertex][j] == 1 && getVertexColor(j) == i) {
					isUsedByNeighbour = true;
					break;
				}
			}
			//System.out.println("color "+i+" "+isUsedByNeighbour);
			if(!isUsedByNeighbour) {
				availableColors[count] = i;
				count++;
			}
		}
		//cut off the unused 0s
		int[] temp = new int[count];
		System.arraycopy(availableColors,0, temp,0,count);
		availableColors = temp;
		return availableColors;
	}
	public int getVertexColor(int v){
		return coloring[v];
	}

	public boolean isFullyColored(){
		return coloredVertices >= coloring.length;
	}
	public void setVertexColor(int v, int color){
		if(color != 0 && v != coloring[v])
			coloredVertices++;
		if(color >= usedColors)
			usedColors = color+1;
		coloring[v] = color;
	}
	public void restoreInitialVertexPositions(){
		int[][] pos = GraphUtil.setCoordinates(vertices.length,0);
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex(pos[i][0],pos[i][1]);
		}
	}
	/**
	 * draws the graph's vertices and edges
	 * @param g a Graphics2D object from a image/canvas to draw on
	 */
	public void draw(Graphics2D g) {
		// Draw all the edges of the graph.
		g.setColor(edgeColor);
		for (int i = 0; i < neighbours.length; i++) {
			if (!vertices[i].isHighlighted()) {
				for (int neighbour : neighbours[i]) {
					g.drawLine(vertices[i].getCX(), vertices[i].getCY(), vertices[neighbour].getCX(), vertices[neighbour].getCY());
				}
			}
		}
		// Draw all the vertices except the one that's highlighted (if there is a highlighted vertex) .
		int highlightedVertex = -1;
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i].isHighlighted()) {
				highlightedVertex = i;
			}
			else {
				vertices[i].draw(g, COLORS[coloring[i]]);
			}
		}
		// If we find a highlighted vertex then:
		if (highlightedVertex != -1) {
			// Store it and initialize a list of it's neighbours
			Vertex v = vertices[highlightedVertex];
			Vertex n;
			List<Integer> nList = new LinkedList<>();
			// Highlight all it's incident edges
			g.setColor(edgeHighlightColor);
			g.setStroke(new BasicStroke(3));
			for (int i = 0; i < adjacencyMatrix.length; i++) {
				if(adjacencyMatrix[highlightedVertex][i] == 1) {
					n = vertices[i];
					g.drawLine(v.getCX(), v.getCY(), n.getCX(), n.getCY());
					nList.add(i);
				}
			}
			nList.add(highlightedVertex);
			g.setStroke(new BasicStroke(1));
			// Redraw all the neighbours
			for (int neighbour : nList) {
				// TODO neighbour.setBorderColor(Vertex.)
				vertices[neighbour].draw(g,COLORS[getVertexColor(neighbour)]);
			}
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
	public void flushColors(){
		for (int i = 0; i < coloring.length; i++) {
			coloring[i] = 0;
		}
		coloredVertices = 0;
	}
	/**
	 * Resolves the index of a vertex to the corresponding instance of the vertex class
	 * @param v the index of a vertex (must be >= 0 and < n)
	 * @return returns the vertex
	 */
	public Vertex getVertex(int v){
		return vertices[v];
	}

	/**This method makes the adjacency matrix, in the same format as we had it in the previous phase, therefore we can immediately start using our algorithms
	 * @return the adjMatrix
	 */
	private int[][] getAdjacencyMatrix() {
		if (adjacencyMatrix == null) {
			int[][] newAdjMatrix = new int[neighbours.length][neighbours.length];
			for (int i = 0; i < neighbours.length; i++)
				for (int neighbour : neighbours[i]) {
					newAdjMatrix[i][neighbour] = 1;
					newAdjMatrix[neighbour][i] = 1;
				}
			return newAdjMatrix;
		}else
			return adjacencyMatrix;
	}
	//just here for debugging
	public static void main(String[] args){
		Graph g = GraphUtil.generateRandomGraph(10,20);
		Game game = new Game();
		//game.gamestate.replaceState();
		game.gamestate.changeState(GameState.INGAME);
	}
}