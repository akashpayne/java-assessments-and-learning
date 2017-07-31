
/**
 * A simple queue, can be bounded
 * 
 * @author Stefan Kahrs
 * @version 1
 */
public interface Queue<E>
{
    /**
     * @param e is added to queue at the end
     * @returns false if queue is already full
     */
    boolean add(E e);
    boolean isFull();
    boolean isEmpty();
    
    int capacity();
    
    /**
     * removes the head of the queue and returns it
     * returns null if queue is empty
     */
    E poll();
}
