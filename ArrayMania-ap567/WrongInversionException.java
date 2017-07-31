
/**
 * Write a description of class WrongInversionException here.
 * 
 * @author ap567
 * @version 1.0
 */
public class WrongInversionException extends Exception 
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class WrongInversionException
     */
    public WrongInversionException(String message)
    {
        super(message);
    }

    public String toString()
    {
        return "Wrong Inversion Exception : " + this.getMessage();
    }
}
