/**
 * This file contains an adjacency matrix that represents a graph. It is a very simple 
 * way of decribing a graph, but makes the programming of graph algorithms a lot easier
 * to understand.
 */
public class Graph
{
    
    /** the constant INF is used by any algorithm that needs it, e.g. Dijkstra
     */
    public final static int INF = 9999999;
    
    /** this is where you have to declare the graph.
     * This example is for the graph used in the lecture examples. You must
     * change it to the one required for the class exercise.
     */
    private int[][] graph = {  
                                {INF,INF,INF,2,5,INF,3,INF}, // 0 -> 2,4,6
                                {INF,INF,4,INF,3,1,INF,1}, // 1 -> 2,4,5,7
                                {INF,INF,INF,2,INF,INF,3,INF}, // 2 -> 3,6
                                {2,INF,2,INF,INF,INF,INF,INF}, // 
                                {5,3,INF,INF,INF,INF,INF,INF}, // 
                                {INF,1,INF,INF,INF,INF,INF,2}, // 5 -> 7
                                {3,INF,3,INF,INF,INF,INF,INF}, // 
                                {INF,1,INF,INF,INF,INF,2,INF} // 
                            };
    /* {
     * {0,0,0,2,5,0,3,0},  // 0 -> 2,4,6
     * {0,0,4,0,3,1,0,1},  // 1 -> 2,4,5,7
     * {0,0,0,2,0,0,3,0},  // 2 -> 3,6
     * {2,0,2,0,0,0,0,0},
     * {5,3,0,0,0,0,0,0},
     * {0,1,0,0,0,0,0,2},  // 5 -> 7
     * {3,0,3,0,0,0,0,0},
     * {0,1,0,0,0,0,2,0} }                         
     */                
                            

    public int[][] getGraph(){
        return graph;
    }
                               
}
