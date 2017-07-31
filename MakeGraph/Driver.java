/**
 * This is the driver class you need to use to print your graph.
 * DO NOT CHANGE IT. It is an example of how not to follow coding standards
 * and has been botched from an old program of mine.
 * 
 * @param = the constructor requires your login to be entered as a character
 * string, e.g. "jsc6"
 * 
 * The edges of your eight vertex graph will be printed as soon as you run 
 * this constructor. It will have 9 +/- edges.
 * 
 * @author = Johnny Crawford.
 */

public class Driver
{
    
    Driver (String login)
    {     
        int seed = 0;
        for (int i = 0; i < login.length() ; i++)
        {
            seed *= seed;
            seed += login.codePointAt(i);
        }
       new MakeGraph(8,100,seed).generate();
    }
    
    public static void main (String[] args){
 
    new Driver(args[0]);
    
    }
    
}