
/**
 * Write a description of class ArrayQueue here.
 * 
 * @author Stefan Kahrs
 * @version 1
 */
public class ArrayQueue<E> implements Queue<E>
{
    /**
     * the array data is where all the entered values go;
     * because of issues with generic arrays we use an array with component type Object
     */
    private Object data[];
    private int top=0; // top of queue
    private int length=0; // length of queue

    public ArrayQueue(int capacity) {
        if (capacity<1) throw new IllegalArgumentException();
        data=new Object[capacity];
    }

    /**
     * @param e is added to queue at the end
     * @returns false if queue is already full
     */
    public boolean add(E e) {
        if (isFull()) return false;
        data[(top+length)%data.length] = e;
        length++;
        return true;
    }

    public boolean isFull() {
        return length==data.length;
    }

    public boolean isEmpty() {
        return length==0;
    }
    
    public int capacity() {
        return data.length;
    }

    /**
    * removes the head of the queue and returns it
    * returns null if queue is empty
    */
    @SuppressWarnings("unchecked")
    public E poll(){
        // to be written
        // note that this methodd will have to cast
        // an object stored in the data array to type E
        if (isEmpty()) return null;
        E result = (E) data[top];
        top = (top+1) % data.length;
        length-- ;
        return result;
    }
}
