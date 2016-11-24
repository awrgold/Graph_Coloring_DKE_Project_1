import java.util.*;
import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class Greedy {

        private LinkedList[] adjList;             //   The adjacency list is taken as a parameter of the constructor.
        private int[] vertexPermutation;          //     The permutation is not set with the constructor, making the same object
                                                  //      viable for computing a coloring with different permutations.
        private int n;  //number of vertices

        public ArrayList<LinkedList<Integer>> S;          //    A set of color sets: my choice of format for representing a graph coloring.
        public int chromaticNumber;                       //        S and chromaticNumber will be considered output, accessible as public fields of the Greedy object.



        public Greedy(LinkedList[] adjList)         // constructor
        {
            this.adjList = adjList;
            this.n = adjList.length;
            S = new ArrayList<LinkedList<Integer>>();

        }


        public void search()                  // This method initializes the computation of a feasible coloring.
        {                                     // A method call with no parameter will make the algorithm process the vertices in ascending order
            S.clear();
            int[] e = new int[n];
            for (int i = 0; i < n; i++)
                e[i] = i;
            this.vertexPermutation = e;
            greedyAlgorithm();
        }

        public void search(int[] inputPermutation)            // Overloaded search method which allows us to input a specific ordering of vertices.
        {
            S.clear();
            this.vertexPermutation = inputPermutation;
            greedyAlgorithm();
        }

        public int[] toArray()                                // A method that returns a permutation of the vertices such that
        {                                                     // the previously colored vertices are clustered together by color.
            int[] colorArray = new int[n];
            int j = 0;                                            // With this permutation we try to obtain better output from the Greedy algorithm.
            for (LinkedList<Integer> cClass : S) {
                for (Integer v : cClass) {
                    colorArray[j] = v;
                    j++;
                }

            }
            return colorArray;
        }

        public void toArray(int[] array)                      // A void method, just the same as the one above. This method will change an array of sufficent size.
        {
            int j = 0;
            for (LinkedList<Integer> cClass : S) {
                for (Integer v : cClass) {
                    array[j] = v;
                    j++;
                }
            }
        }


        private void greedyAlgorithm()
        {
            for (int i = 0; i < n; i++)
            {
                int j = 0;
                while ( j < S.size() )
                {
                    if ( safeToAdd(vertexPermutation[i], S.get(j)) )
                    {
                        S.get(j).add((Integer) vertexPermutation[i]);
                        break;
                    } else
                        j++;
                }
                if (j >= S.size())
                {
                    LinkedList<Integer> colorClass = new LinkedList();
                    colorClass.add((Integer) vertexPermutation[i]);
                    S.add(colorClass);
                }
            }
             this.chromaticNumber = S.size();
        }


        public boolean safeToAdd (int vertex, LinkedList<Integer> colorClassJ)
        {
            for (Integer i : colorClassJ) {
                if ( adjList[vertex].contains(i) )
                    return false;
            }
            return true;
        }


}

/*
  ArrayList<LinkedList<Integer>> greedyColoring = new ArrayList();

  greedyColoring = greedyAlgorithm(greedyColoring, testCase, adjList);

  int i = 1;
  for (LinkedList<Integer> a : greedyColoring) {
      System.out.println("\nColor "+i+": ");
      for (Integer v : a) {
          System.out.print(v+" ");
      }
      i++;
  }

  System.out.println("\nFirst iteration greedy number is: "+greedyColoring.size());

  int[] newPerm = new int[n];
  int j = 0;
  for (LinkedList<Integer> cClass : greedyColoring) {
      for (Integer v : cClass) {
          newPerm[j] = v;
          j++;
      }
  }
  greedyColoring = greedyAlgorithm(new ArrayList(), newPerm, adjList );

  int k = 1;
  for (LinkedList<Integer> a : greedyColoring) {
      System.out.println("\nColor "+i+": ");
      for (Integer v : a) {
          System.out.print(v+" ");
      }
      k++;
  }

  System.out.println("Second iteration gives: "+greedyColoring.size());

}*/
