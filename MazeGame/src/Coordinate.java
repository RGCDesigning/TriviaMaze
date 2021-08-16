import java.io.Serializable;

public class Coordinate implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    protected final int myX;
    
    /**
     * 
     */
    protected final int myY;
    
    public Coordinate(final int theX, final int theY)
    {
        myX = theX;
        myY = theY;
    }
    
    public String toString()
    {
        return "(" + myX + ", " + myY + ")";
    }
}
