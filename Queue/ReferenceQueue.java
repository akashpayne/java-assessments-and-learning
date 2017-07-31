
/**
 * reference implementation, for comparison
 * 
 * @author Stefan Kahrs
 * @version 1
 */
public class ReferenceQueue<E> extends java.util.concurrent.ArrayBlockingQueue<E> implements Queue<E>
{
    public boolean add(E e) {
        return offer(e);
    }

    public boolean isFull() {
        return remainingCapacity()==0;
    }

    public boolean isEmpty() {
        return size()==0;
    }

    public E poll() { return super.poll(); }
        
    public int capacity() { return size()+remainingCapacity(); }
    
    public ReferenceQueue(int capacity) {
        super(capacity);
    }

}
