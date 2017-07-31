
/**
 * Exceptions when edges are created
 * 
 * @author Stefan Kahrs
 * @version 1
 */
public class EdgeException extends RuntimeException
{
    
    public EdgeException(String message)
    {
        super(message);
    }

    final public static EdgeException LOOP =
        new EdgeException("self serving");
    final public static EdgeException OUTSIDE =
        new EdgeException("vertex outside graph");
    final public static EdgeException ORDER =
        new EdgeException("vertices in edge in wrong order");
}
