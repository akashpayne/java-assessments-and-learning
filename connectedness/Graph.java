
/**
 * An undirected Graph represented with an adjacency matrix
 * 
 * @author Stefan Kahrs
 * @version 2
 */
import java.io.*;
import java.util.*;

public class Graph
{
    // instance variables - replace the example below with your own
    private int matrix[][];
    private int parent[];
    private int order;
    private VertexGenerator vg;

    /**
     * Constructor for objects of class Graph
     */
    public Graph(VertexGenerator vg)
    {
        order=vg.order();
        matrix=new int[order][order];
        parent=new int[order];
        this.vg=vg;
    }
 
    public void addEdge(int a,int b)
    {
        if (a>b) throw EdgeException.ORDER;
        if (a==b) throw EdgeException.LOOP;
        if (b>=order || a<0) throw EdgeException.OUTSIDE;
        matrix[a][b]++;
        union(root(a),root(b));
    }
    
    private int root(int a)
    {
        int k=a+1;
        while(parent[k-1]>0) { k=parent[k-1]; }
        return k-1;
    }
    
    private void union(int a,int b)
    {
        if (a==b) return;
        if (parent[a]<parent[b]) {
            parent[a]+=parent[b]-1;
            parent[b]=a+1;
        } else {
            parent[b]+=parent[a]-1;
            parent[a]=b+1;
        }
    }
    
    public String vertexToString(int n) //spelling mistake corrected
    {
        return vg.getV(n);
    }
    

    /**
     * save current graph to a file
     * @param filename is the name of the target file
     * @throws IOException if the target file cannot be created
     */
    public void saveToFile(String filename) throws IOException
    {
        // not implemented
    }
    
    /**
     * add edges to the graph to make it an Euler graph
     */
    public void makeEulerGraph()
    {
        // not implemented
    }

    /**
     * adds random edges to the graph
     * @param numberofedges the number of edges that need to be generated and added via the method addEdge
     */
    public void randomEdges(int numberofedges)
    {
        for(int i=0; i<numberofedges; i++) {
            int a=random.nextInt(order);
            int b=random.nextInt(order-1);
            if (a<b) addEdge(a,b);
            else if (a>b) addEdge(b,a);
            else addEdge(a,order-1);
        }
    }
    
    // a generator for random numbers
    private Random random=new Random();
    
    /**
     * @return the number of connected components of the graph
     * this can be repeatedly called as the graph is growing by edges
     */
    public int connectedComponents()
    {
        int counter=0;
        for (int b:parent) if (b<=0) counter++;
        return counter;
    }
    
    // for no particular good reason the magic number is set to 42
    public static final int MAGICNUMBER = 42;
    
    /**
     * @param graphOrder the number of vertices the random graphs should have
     * @param graphEdges the number of edges they should have
     * @return the average number of connected components of MAGICNUMBER many random graphs of that description
     */
    public static double connectedAverage(int graphOrder, int graphEdges)
    {
        double sum=0.0; // type double to prevent int division later
        VertexGenerator vg=new VertexGenerator(graphOrder);
        for (int i=0; i<MAGICNUMBER; i++) {
            Graph g=new Graph(vg);
            g.randomEdges(graphEdges);
            sum += g.connectedComponents();
        }
        return sum / MAGICNUMBER;
    }
        
}
