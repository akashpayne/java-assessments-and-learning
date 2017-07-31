
/**
 * Write a description of class QueueTest here.
 * 
 * @author Stefan Kahrs
 * @version 1
 */
import java.util.Random;
public class QueueTest
{
    // instance variables - replace the example below with your own
    private Queue<Integer> ref;
    private Queue<Integer> candidate;
    private Random rg=new Random();
    
    private static final int SIZE=1000;
    private int data[]=new int[SIZE];
    private int datap;
    private boolean decs[]=new boolean[SIZE];
    private int tracep= 0;
    private int reason=0;

    /**
     * Constructor for objects of class QueueTest
     */
    public QueueTest(Queue<Integer> candidate) throws Exception
    {
        this.candidate=candidate;
        if (!candidate.isEmpty()) {
            throw new Exception("candidate queue object must be empty to begin with");
        }
        ref=new ReferenceQueue<Integer>(candidate.capacity());
    }

    public boolean fillTest() {
        datap=0; tracep= 0; reason=0;
        for (int i=0; i<SIZE; i++) {
            int b=rg.nextInt(4);
            decs[i]=b>0;
            if (b>0) {
                Integer a=Integer.valueOf(rg.nextInt(10000));
                data[datap++]=a;
                boolean b1=ref.add(a);
                boolean b2=candidate.add(a);
                if (b1!=b2) { tracep=i; reason=b1?1:2; return false; }
            } else {
                Integer a1=ref.poll();
                Integer a2=candidate.poll();
                if (a1!=a2) { tracep=i; reason=3; return false; }
            }
        }
        return true;
    }
    
    public void failTrace() {
        switch (reason) {
            case 0: System.out.println("No problem reported"); return;
            case 1: System.out.println("Queue should not have been full"); break;
            case 2: System.out.println("Queue should have been full"); break;
            case 3: System.out.println("Wrong data polled from queue");
        }
        int j=0;
        System.out.println("TRACE:");
        for (int k=0; k<=tracep; k++) {
            if (decs[k]) {
                System.out.println("put("+data[j++]+")");
            } else {
                System.out.println("poll");
            }
        }
    }
}
