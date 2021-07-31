
public class Coordinate
{
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
