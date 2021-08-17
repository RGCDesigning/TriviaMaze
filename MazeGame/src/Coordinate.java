import java.io.Serializable;

public class Coordinate implements Serializable
{
    /**
     * The default serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The stored x variable.
     */
    protected final int myX;
    
    /**
     * The stored y variable.
     */
    protected final int myY;
    
    public Coordinate(final int theX, final int theY)
    {
        myX = theX;
        myY = theY;
    }
    
    @Override
    public String toString()
    {
        return "(" + myX + ", " + myY + ")";
    }
}
