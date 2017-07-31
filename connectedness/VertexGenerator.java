
/**
 * turning strings into integers, and back
 * 
 * @author Stefan Kahrs
 * @version 2
 */
import java.util.*;
class VertexGenerator {
	private String[] allVertices;
	private int vcount=0;
	public int getNum(String vertex) {
		char[]num26=vertex.toCharArray();
		int number=0;
		for(int i=num26.length-1; i>=0; i--)
		{
		    number=26*number+(num26[i]-'A');
		}
		return number;
	}
	public String getV(int n) { return allVertices[n]; }
	public int order() { return vcount; }
	
	VertexGenerator(int order) {
	    vcount=order;
	    allVertices=new String[order];
	    fill();
	   }
	
	 private void fill() {
        double log26=Math.log(vcount+1)/Math.log(26.0);
        int len=(int)Math.ceil(log26);
        char[] names=new char[len];
        Arrays.fill(names,'A');
        for (int i=0; i<vcount; i++) {
            allVertices[i]=new String(names);
            for (int j=0; j<len; j++) {
                if (names[j]=='Z') names[j]='A';
                else { names[j]++; break; }
            }
        }
    }
}
