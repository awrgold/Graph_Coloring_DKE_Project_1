public class BacktrackGreedy {
    private int[][] neighbours;
    private int n;
    public int[] coloring;
    public int maxColor;

	public BacktrackGreedy(int[][] adjMatrix){
		coloring = new int[n];
		n = adjMatrix.length;
		neighbours = new int[n][];
		for(int i = 0; i<n; i++){
			int p = 0;
			for(int k = 0;k<n;k++){
				if(adjMatrix[i][k] == 1)
					p++;
			}
			neighbours[i] = new int[p];
			int m = 0;
			for (int k = 0; k < n; k++){
				if (adjMatrix[i][k] == 1){
					neighbours[i][m] = k;
					m++;
				}
			}
		}
        coloring = new int[n];
        for(int i = 0; i<n;i++)
        	coloring[i] = i+1;
        maxColor = n;
    }

	//(maybe false, but take care) IMPORTANT: as a side effect, colors are going to be added to the coloring array, at the end it'll contain an optimal coloring
	public void backtrackSearch(int[] coloring, int maxColor, int startIndex, int[] vertexPermutation){
		backtrackGreedyAlgorithm(startIndex, maxColor, coloring, vertexPermutation);
	}

    public void backtrackSearch(){
    	int[] coloring = new int[n];
    	for(int i = 1; i<n;i++)
    		coloring[i] = -1;
    	coloring[0] = 1;
    	backtrackGreedyAlgorithm(1,1, coloring);
    }

    //implement multi-core support, e.g.
     private void backtrackGreedyAlgorithm(int currIndex, int maxColor, int[] coloring){
    	 if(currIndex == n){
    		if(maxColor < this.maxColor){
    			System.out.println("curr maxcolor: "+maxColor);
    			this.maxColor = maxColor;
    			for(int i = 0; i<n;i++)
    				this.coloring[i] = coloring[i];
    		}
    	 } else if(maxColor < this.maxColor) {
    		 for(int c = 1; c<=maxColor;c++){
    			 if(safeToAdd(currIndex,coloring,c)){
    				 coloring[currIndex] = c;
    				 backtrackGreedyAlgorithm(currIndex+1,maxColor,coloring);
    			 }
    		 }
    		 maxColor++;
    		 coloring[currIndex] = maxColor;
    		 backtrackGreedyAlgorithm(currIndex+1,maxColor,coloring);
    	 }
    }

     private void backtrackGreedyAlgorithm(int currIndex, int maxColor, int[] coloring, int[] vertexPermutation){
    	 if(currIndex == n){
    		if(maxColor < this.maxColor){
    			System.out.println("curr maxcolor: "+maxColor);
    			this.maxColor = maxColor;
    			for(int i = 0; i<n;i++)
    				this.coloring[i] = coloring[i];
    		}
    	 } else if(maxColor < this.maxColor) {
    		 for(int c = 1; c<=maxColor;c++){
    			 if(safeToAdd(vertexPermutation[currIndex],coloring,c)){
    				 coloring[vertexPermutation[currIndex]] = c;
    				 backtrackGreedyAlgorithm(currIndex+1,maxColor,coloring, vertexPermutation);
    			 }
    		 }
    		 maxColor++;
    		 coloring[vertexPermutation[currIndex]] = maxColor;
    		 backtrackGreedyAlgorithm(currIndex+1,maxColor,coloring);
    	 }
    }
 
    private boolean safeToAdd(int vertex, int[] coloring, int color){
    	for(int v : neighbours[vertex]){
    		if(coloring[v] == color)
    			return false;
    	}
    	return true;
    }
}