import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class GraphUtil {
    private static boolean DEBUG = false;
    /**
     * Reads-in the graph from a file, we make use of the code provided to us in the beginning of the project.
     * @param file A text file containing a graph in the format given for phase 1
     * @return Graph...
     */
    public static Graph readGraphFromFile(File file){  // TODO: 12/3/2016 Have to add protection against wrong file format being passed
        String COMMENT = "//";
	Random r = new Random();
        if( file == null )
        {
            System.out.println("Error! Empty file.");
            System.exit(0);
        }
        boolean seen[] = null;

        int n = -1; //number of vertices
        int m = -1; //number of edges

        try 	{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String record;

            //! THe first few lines of the file are allowed to be comments, staring with a // symbol.
            //! These comments are only allowed at the top of the file.

            //! -----------------------------------------
            while (!((record = br.readLine()) == null))
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
			//Get the coordinates from the method that calculates them
			int[][] coordinates = setCoordinates(n);
			//Insert the coordinates
			for(int i = 0; i<n; i++){
				vertices[i] = new Vertex(coordinates[i][0],coordinates[i][1]);
				//old way of positioning (can be recovered if necessary)
				//int x = r.nextInt(Game.WIDTH - Vertex.STANDARD_DIAMETER);
                		//int y = r.nextInt(Game.HEIGHT - Vertex.STANDARD_DIAMETER);
				//vertices[i] = new Vertex(x,y);
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
                if(DEBUG) System.out.println(COMMENT + " Edge: "+ u +" "+v);
            }
            String surplus = br.readLine();
            if( surplus != null ){
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
                    System.out.println("Vertex: " +i+ " adjacent to: " + Arrays.toString(neighbours[i]));
                }
            }
            return new Graph(vertices, neighbours);
        }
        catch (IOException ex)
        {
            // catch possible io errors from readLine()
            System.out.println("Error! Problem reading file "+ file.getName());
            System.exit(0);
        }

        for( int x=1; x<=n; x++ )
        {
            if(!seen[x])
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
        Random r = new Random();
	    Vertex[] vertices = new Vertex[n];
		int[][] neighbours = new int[n][m];
		
		//Get the coordinates from the method that calculates them
		int[][] coordinates = setCoordinates(n);
		//Insert the coordinates
		for(int i = 0; i<n; i++){
			//old way of positioning (can be recovered if necessary)
			//int x = r.nextInt(Game.WIDTH - Vertex.STANDARD_DIAMETER);
                	//int y = r.nextInt(Game.HEIGHT - Vertex.STANDARD_DIAMETER);
			//vertices[i] = new Vertex(x,y);


			vertices[i] = new Vertex(coordinates[i][0],coordinates[i][1]);
		}
        
        //make a path through all vertices, so that the graph is connected
        for(int i = 0; i<n-1;i++){
            neighbours[i][0] = i+1;
        }

        //generate m-(n-1) (=the edges left over after the path) edges randomly
	    for (int i = 0; i < m - n + 1; i++) {
            //randomly select two vertices to create a edge between them
            int u = r.nextInt(n);
            int v = r.nextInt(n);
            //check for u==v
            while (u == v) {
                v = r.nextInt(n); //the random assigned vertex to v has to differ from u COUNTING
            }
            int min = Math.min(u, v);
            int max = Math.max(u, v);
            //Check if the created edge already existed
            boolean exists = false;
            for (int j = 0; j < neighbours[min].length; j++) {
                if (neighbours[min][j] == max) { //COUNTING
                    exists = true;
                    if (DEBUG) {
                        System.out.println("FALSE " + u + " " + v);
                    }
                    i--; //Generate random vertices for edge[i] again
                    break;
                }
            }
            if (!exists) {
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

	/**
	 * This method calculates the chromatic number for a certain graph based on the adjacency matrix,
	 * therefore we use the algorithms of the previous phase, of which BacktrackGreedy is improved.
	 *@param adjMatrix adjacency matrix of the graph
	 *@return returns the chromatic number or the lowest upperbound we found
	*/
	public static int calculateChromaticNR (int[][] adjMatrix){
		int chromaticNR;
		//Check for complete graph
		/*if ( ( ( n*(n-1) ) / 2) == m ){
			chromaticNR = n;
		}*/
		//Check for bipartite graph
		if ( is2colorable(adjMatrix) ){
			chromaticNR = 2;
		}
		//Otherwise we use the other algorithms
		else{
			BacktrackGreedy backtrack = new BacktrackGreedy(adjMatrix);
			int welshPowellOutput = WelshPowell.getchromaticnr(adjMatrix);
			System.out.println ("Welsh Powell gives: "+welshPowellOutput);
			int backtrackOutput = backtrack.maxColor;
			System.out.println ("Bakctrack Greedy gives: "+backtrackOutput);
			chromaticNR = Math.min(backtrackOutput, welshPowellOutput);
		}

		return chromaticNR;
	}


    private static boolean is2colorable(int[][] adjMatrix) {
        int[] colorArr = new int[adjMatrix.length];
        for (int i = 0; i < adjMatrix.length; i++)
        {
            colorArr[i] = -1;
        }
        colorArr[0] = 1;
        LinkedList<Integer> q = new LinkedList<>();
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
	/**
	 * Calculates x and y coordinates for the vertices of a graph
	 * @param n the number of vertices
	 * @return the matrix with the x and y coordinates
	 */
	public static int[][] setCoordinates(int n){
		int [][] coordinates = new int[n][2]; //create the array that contains the number of x-y coordinates
		int noOfCircles = 1; //the amount of circles
		int radius; //the radius
		int distance; //the distance between all vertices
		int cX = Game.WIDTH/2; //Define the center x-coordinate
		int cY = Game.HEIGHT/2; //Define the center y-coordinate
		final int MAX = 50;

		//set the amount of circles
		int sumA = MAX;
		while(n>sumA){
			noOfCircles++;
			sumA += sumA-10;
			if(sumA<0){
				noOfCircles = (int) (n/MAX+1);
				break;
		final int MAX = 50;

		//set the amount of circles
		int sumA = MAX;
		while(n>sumA){
			noOfCircles++;
			sumA += sumA-10;
			if(sumA<0){
				noOfCircles = (int) (n/MAX+1);
				break;
			}
		}

		//set the radius
		if(Game.HEIGHT > Game.WIDTH){
			radius = (int) ((Game.WIDTH/2)-Vertex.STANDARD_DIAMETER);
		}else{
			radius = (int) ((Game.HEIGHT/2)-Vertex.STANDARD_DIAMETER);
		}

		//set the distance
		distance = radius/noOfCircles-Vertex.STANDARD_DIAMETER;

		//set the coordinates
		int sumB = Math.min(MAX,n);
		int sumC = sumB;
		int j=0;

		//if there less than 250 vertices, this works properly
		if(n<=250){
			for(int i=0;i<n;i++){
				if(i>=sumB){
					j++;
					System.out.println("Action Performed: "+j);
					sumB += sumB-10;
					sumC -= 10;
					sumC = Math.min(sumC,n-i);
					//k=0;
				}
				System.out.println(distance+" "+j);
				int x = (int) Math.round(cX + (radius-distance*j) * Math.cos(i* 2*Math.PI / sumC))-Vertex.STANDARD_DIAMETER/2; //x coordinate
				coordinates[i][0] = x;
				System.out.println("x: "+x);
				int y = (int) Math.round(cY + (radius-distance*j) * Math.sin(i* 2*Math.PI / sumC + Math.PI))-Vertex.STANDARD_DIAMETER/2; //y coordinate
				coordinates[i][1] = y;
				System.out.println("y: "+y);



			}
		//otherwise, just show one single graph
		}else{
			for(int i=0;i<n;i++){
				coordinates[i][0] = (int) Math.round(cX + (radius-distance*0) * Math.cos(i* 2*Math.PI / n))-Vertex.STANDARD_DIAMETER/2; //x coordinate
				coordinates[i][1] = (int) Math.round(cY + (radius-distance*0) * Math.sin(i* 2*Math.PI / n + Math.PI))-Vertex.STANDARD_DIAMETER/2; //y coordinate
			}
		}
		return coordinates;
	}
}
