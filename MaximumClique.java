import java.util.ArrayList;

public class MaximumClique {
	private int[][] adjMatrix;
	//here the maxClique is going to be stored, after search() has been performed, it contains all vertices from the maximum clique
	public int[] maxClique = new int[0];
	//size of maxClique after search() has been performed
	public int maxCliqueSize;
	//get the adjmatrix and store as instance variable
	public MaximumClique(int[][] adjMatrix){
		this.adjMatrix = adjMatrix;
	}
	//call this to trigger the search for the largest clique
	public void search(){
		//first time enlarge gets called, feed it with all vertices
		ArrayList<Integer> allNodes = new ArrayList<Integer>();
		for(int i = 0;i<adjMatrix.length;i++)
			allNodes.add(i);
		enlarge(new ArrayList<Integer>(), allNodes);
		maxCliqueSize = maxClique.length;
	}
	//cliquemembers contains vertices which are a complete subgraph, commonNeighbours is the intersection of all neighbourhoods of all vertices in the graph
	//commonneighbours contains thus all vertices which can be added to cliquemembers while maintaining its completeness
	private void enlarge(ArrayList<Integer> cliqueMembers, ArrayList<Integer> commonNeighbours){
		//loop through all common neighbours, if commonneighbour.size+cliquemembers.size is too small, it cannot become a larger clique than the already found one, thus don't even try
		while(!commonNeighbours.isEmpty() && commonNeighbours.size()+cliqueMembers.size() > maxClique.length){
			//take the last vertex, otherwise the time needed for computing explodes, because all elements have to be moved when v is removed at the end
			int v = commonNeighbours.get(commonNeighbours.size()-1);
			//take a element of commonneighbours, union cliquemembers and this element. after that cliquemembers is still complete, because the added element is a neighbour of all existing vertices in that subgraph
			cliqueMembers.add(v);
			//now take a look which vertices could potentionally added to the enlarged cliquemembers
			ArrayList<Integer> newCommonNeighbours = new ArrayList<Integer>();
			for(int w : commonNeighbours){
				if(adjMatrix[v][w] == 1)
					newCommonNeighbours.add(w);
			}
			//if there's nothing to add...
			if(newCommonNeighbours.isEmpty()){
				//... check whether it is a new possible solution...
				if(cliqueMembers.size() > maxClique.length){
					//... and if so fill maxclique with it
					maxClique = new int[cliqueMembers.size()];
					for(int i = 0;i<maxClique.length;i++)
						maxClique[i] = cliqueMembers.get(i);
				}
			}	else {
				//if there are still nodes which can be added, while cliquemembers stays complete, recursivly do that
				enlarge(cliqueMembers, newCommonNeighbours);
			}
			//and remove the element investigated from both sets, because if it was a part of the solution, it already has been added to maxclique
			cliqueMembers.remove((Integer) v);
			commonNeighbours.remove((Integer) v);
		}
	}
}
