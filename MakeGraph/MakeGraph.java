/**
 * This class will generate a graph based on your "log-in" and print out a list of edges.
 * DO NOT MODIFY IT - It is an example of how not to follow coding standards
 * and has been botched from an old program of mine.
 * @author = Johnny Crawford.
 */

import java.lang.Math;
import java.util.Random;
import java.util.*;

public class MakeGraph {

    private static final double alpha = 0.2;
    private static final double beta = 0.1;
    private static final long seed = 1011950;
    public static final int INFINITY = 99999;
    private int scale;
    private int gridSize = 600;
    private Random randomNumber;
    private int[][] network;
    
    private int[] xval;
    private int[] yval;
    private int nodes = 10;

    MakeGraph(int numNodes,int gridSize, int seed){

        nodes = numNodes;
        this.gridSize = gridSize;
        this.scale = gridSize/numNodes;
        randomNumber = new Random(seed);

    }

    public void setNumNodes(int numNodes){
        nodes = numNodes;
    }
    
    public int getGridSize(){
        return gridSize;
    }

    public void generate(){
        
        double lval = Math.sqrt(2*nodes*nodes);
        network = new int[nodes][];
        for(int i = 0; i < nodes; i++)
            network[i] = new int[nodes];
        xval = new int[nodes];
        yval = new int[nodes];
        do {
            for (int i = 0; i < nodes; i++)
                for (int j = 0; j < nodes; j++)
                        network[i][j] = 0;
                                       

            for (int i=0;i<nodes;i++){
                do {
                    xval[i] = randomNumber.nextInt(nodes);
                    yval[i] = randomNumber.nextInt(nodes);
                }
                while ( !( (network[xval[i]][0] == 0) && (network[yval[i]][1] == 0) ) );
                network[xval[i]][0] = 1;
                network[yval[i]][1] = 1;
                
            }

            for (int i = 0; i < nodes; i++)
                for (int j = 0; j < nodes; j++)
                    if (i==j)
                        network[i][j] = 0;
                    else
                        network[i][j] = INFINITY;
            
        
            for (int i = 0; i < nodes; i++)
                for (int j = 0; j < nodes ; j++){
                    if (i!=j)
                      {
                        double xdist = Math.abs(xval[i]-xval[j]);
                        double ydist = Math.abs(yval[i]-yval[j]);

                        double dist = Math.sqrt(xdist*xdist + ydist*ydist);
                        double expval = -dist/(lval*alpha);
                        double e = Math.exp(expval);
                        double prob = ((beta * ((nodes/2) + 50))/nodes) * e;
                        
                        double rand = randomNumber.nextDouble();

                       
                        if (rand < prob){
                            
                            network[i][j] = (int)dist;
                            network[j][i] = (int)dist;
                            }
                        }
                    }
            
        }
        while (!check());

        for (int i = 0; i < network.length ; i++)
        {
            for (int j = i; j < network.length ; j++)
            {
                if ((network[i][j] != INFINITY) && (network[i][j] != 0))
                    System.out.println("Edge [" + i + "]["+ j + "] = " + network[i][j]);
                    
            }
            System.out.println();
        }
        
        return;
    }


    private boolean check(){


        int [][] matrix = new int[nodes][];
        for(int i = 0; i < nodes; i++)
            matrix[i] = new int[nodes];
        for(int i=0;i<nodes;i++)
            for(int j=0;j<nodes;j++)
                matrix[i][j]=network[i][j];
        int item;
        for(int k = 0; k <nodes; k++)
            for(int i =0; i < nodes; i++)
                for(int j = 0; j < nodes; j++){
                    item = matrix[i][k] + matrix[k][j];
                    if (item < matrix[i][j])
                        matrix[i][j] = item;
                }
        
        for(int k = 0; k <nodes; k++)
            for(int i =0; i < nodes; i++)
                for(int j = 0; j < nodes; j++)
                    if (matrix[i][j] >= INFINITY)
                        return false;

        for(int i =0; i < nodes; i++){
            int count = 0;
            for(int j = 0; j < nodes; j++)
                if (!(network[i][j] >= INFINITY) && (network[i][j] != 0)) count++;

            if(count <= 1) return false;
        }
        return true;
    }
        
}

            
