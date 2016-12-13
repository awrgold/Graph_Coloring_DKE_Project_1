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
			int[][] coordinates = setCoordinates(n,0);
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
		int[][] coordinates = setCoordinates(n,0);
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
	/**Made by Jurriaan Berger, 
	 * Calculates x and y coordinates for the vertices of a graph
	 * @param n the number of vertices
	 * @return the matrix with the x and y coordinates
	 */
	public static int[][] setCoordinates(int n, int cntr){
		cntr++;
		int [][] coordinates = new int[n][2];
		int radius;
		if(Game.HEIGHT > Game.WIDTH){
			radius = (int) ((Game.HEIGHT/2)/(Math.sqrt(cntr))-Vertex.STANDARD_DIAMETER/(cntr*1.5));//Can be optimized
		}else{
			radius = (int) ((Game.HEIGHT/2)/(Math.sqrt(cntr))-Vertex.STANDARD_DIAMETER/(cntr*1.5));//Can be optimized
		}
		int cX = Game.WIDTH/2; //Define the center x-coordinate
		int cY = Game.HEIGHT/2; //Define the center y-coordinate
		if(n<(50/cntr)){//Not to much vertices to use only a outer circle
			for(int i=0;i<coordinates.length; i++){
				coordinates[i][0] = (int) Math.round(cX + (radius) * Math.cos(i* 2*Math.PI / n))-Vertex.STANDARD_DIAMETER/2; //x coordinate
				coordinates[i][1] = (int) Math.round(cY + (radius) * Math.sin(i* 2*Math.PI / n + Math.PI))-Vertex.STANDARD_DIAMETER/2; //y coordinate
			}
		}else{//Use inner circles (it might still happen that there are more than 50 vertices placed in the outer circle
			int innerVert;
			int outerVert;
			if(n%2!=0){
				innerVert = n/2;
				outerVert = n/2+1;
			}else{
				innerVert = n/2;
				outerVert = n/2;
			}

			int [][] innerCoordinates = setCoordinates(innerVert,cntr);// set the coordinates of the inner circle(s)
			for(int i=0;i<(outerVert); i++){// set the coordinates of the outer circle
				coordinates[i][0] = (int) Math.round(cX + (radius) * Math.cos(i* 2*Math.PI / outerVert))-Vertex.STANDARD_DIAMETER/2; //x coordinate
				coordinates[i][1] = (int) Math.round(cY + (radius) * Math.sin(i* 2*Math.PI / outerVert + Math.PI))-Vertex.STANDARD_DIAMETER/2; //y coordinate
			}
			for(int i=0; i<n/2;i++){// import the coordinates of the inner circle(s)
				coordinates[i+outerVert][0] = innerCoordinates[i][0];
				coordinates[i+outerVert][1] = innerCoordinates[i][1];
			}
		}

		return coordinates;
	}
}
