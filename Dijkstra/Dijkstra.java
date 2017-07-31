import java.io.*;
import java.util.*;

/**
 * This class can be used to find Dijkstra's SPT in a graph
 * represented by an adjaceny matrix (a two dimentional int array).
 */
public class Dijkstra {

    private int[][] graph; /* this variable references the adjacency matrix for the graph.*/
    
    /**
     * the constructor requires a reference to the adjaceny matrix, for the graph, as a parameter
     */
    Dijkstra (int[][] graph)
    {
           this.graph = graph;
    } 
  
    /**
     * This is the method that you are to complete. It takes, as a paramter, the root of the SPT
     * to be calculated. It returns an array containing the predecessors to each vertex in the tree.
     */
    public int[] shortestPath(int root)
    {
        /** STEP 1: declare the variables you will need here. These include the
         * boolean array to indicate if a vertex is in the tree or not (Hint: vertices),
         * an int array of distances that vertices are from the root via the tree (Hint: distances)
         * and an int array of the vertex predecessor that make up the solution, the SPT, which is
         * given here.....
         */
        int INF = 9999999;
        int[] tree;
        int[] distances;
        boolean[] vertices;
        int index = 0;
        int size = 8; 
            
        
        /** STEP 2: write the statements needed to initialise the "distances", "vertices" and the "tree".
         * Also include a statement to add the "root" to the "tree".
         */
        // create and initialize the distance array
        distances = new int[size];
        for (int i = 0; i < size; i++) distances[i] = INF;
        
        // create and initialize the set of settled nodes
        vertices = new boolean[size];
        for (int i = 0; i < size; i++) vertices[i] = false;
        
        tree = new int[size];
        for (int i = 0; i < size; i++) { 
            distances[i] = graph[root][i]; 
            vertices[i] = true; 
            tree[i] = root; 
        }            
            
            
		/** STEP 3: Now you get into the main procedure, the body of the algoithm.
         * 
         * For each vertex in the graph, you have to do the following:
         *      1.  Find the vertex closest to the root via any vertex already in the tree.
         *      2.  Add that vertex to the tree.
         *      3.  Update the distances to the root of any vertex not already in the tree,
         *          via the vertex just added to the tree
         */
        int min = INF;
        for (int count = 0; count < size; count++) 
        {
            for	(int i = 0; i < size; i++) {	
                if	(vertices[i]) {
                    if	(distances[i] < min) {	
                        min = distances[i];	
                        index = i;	
                    }
                    vertices[index]	=	false;	
                }
            }
            for	(int i=0; i<size; i++) {
                if	(vertices[i]) {	
					if	(distances[i]>distances[index]+graph[index][i]) {	
							distances[i]=distances[index]+graph[index][i];	
							tree[i]=index;	
					}
				}
			}
		}
        /** Step 4: return the tree predecessor array.
         */
        
        return tree;
    }
}
