/**
 * This class is a "driver" to run Dijksta's SPT algorithm
 * It creates a Dijkstra object to which it passes the adjacency matrix of the graph
 * delcared in the Graph object.
 * 
 * It has two methods. One to call Dijkstra's SPT algorithm using a Dijkstra object, which
 * returns the tree array. The other will print the SPT.
 * 
 */
public class Driver
{
    
    private Dijkstra dijkstra;
    private int[] tree;             /* used to save the tree predecessor list. */
    
    Driver ()
    {     
        dijkstra = new Dijkstra(new Graph().getGraph());       
    }
    
    public void run (int root)
    {
       tree = dijkstra.shortestPath(root);
    }
    
    public void printTree()
    {        
        if (tree == null){
            System.out.println("There is no tree to print!!!!!");
            return;
        }
        
        for (int i = 0 ; i < tree.length ; i++)
            System.out.println("Vertex " + i + " is preceeded by vertex " + tree[i]);  
    }
    
        
}
