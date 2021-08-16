import java.io.Serializable;

/**
 * The MapNodeType enum.
 * @author r3mix
 * @version 8.14.21
 */
public enum MapNodeType implements Serializable
{
    /**
     * Represents a clear passage way.
     */
    HALLWAY(' '), 
    
    /**
     * Represents a blocked passage or wall.
     */
    WALL('#'), 
    
    /**
     * Represents a question space that is closed.
     */
    DOOR('D'), 
    
    /**
     * Represents a question space that is now open.
     */
    OPENED_DOOR('d'), 
    
    /**
     * Represents the start of the map.
     */
    ENTRANCE('O'), 
    
    /**
     * Represents the end of the map.
     */
    EXIT('X'), 
    
    /**
     * Represents a visited position during generation.
     * Can also be used to trace path during gameplay.
     */
    VISITED('V');
    
    /**
     * The symbol that is printed in the console.
     */
    private final char mySymbol;
    
    MapNodeType(final char theSymbol)
    {
        mySymbol = theSymbol;
    }
    
    /**
     * Gets the symbol of the MapNodeType.
     * @return Returns the symbol used to represent Node.
     */
    public char getSymbol()
    {
        return mySymbol;
    }
    
}
