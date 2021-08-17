package mazegame;
import java.io.Serializable;

/**
 * The MapNode class.
 * @author r3mix
 * @version 8.14.21
 */

public class MapNode implements Serializable
{
    
    /**
     * The default serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The type of MapNode.
     */
    private MapNodeType myNodeType;
    
    /**
     * Stores the node's coordinates
     */
    private Coordinate myCoordinate;
    
    /**
     * Stores whether or not this node contains the player.
     */
    private boolean isPlayerPos;
    
    MapNode(final MapNodeType theNodeType, final Coordinate theCoordinate)
    {
        myNodeType = theNodeType;
        myCoordinate = theCoordinate;
    }
    
    /**
     * Sets the Node type.
     * @param theNodeType
     */
    public void setNodeType(final MapNodeType theNodeType)
    {
        myNodeType = theNodeType;
    }
    
    /**
     * Gets the Node type.
     * @return Returns the type of Node.
     */
    public MapNodeType getNodeType()
    {
        return myNodeType;
    }
    
    /**
     * Gets the node's coordinate.
     * @return Returns the coordinate attached to this Node.
     */
    public Coordinate getCoordinate()
    {
        return myCoordinate;
    }
    
    /**
     * Gets whether or not the player is present on given node.
     * @return Returns whether or not the player is present.
     */
    protected boolean isPlayerPresent()
    {
        return isPlayerPos;
    }
    
    /**
     * Set the playerPos setting.
     * @param theSetting True or false.
     */
    protected void setPlayerPresent(final boolean theSetting)
    {
        isPlayerPos = theSetting;
    }
    
    @Override
    public String toString()
    {
        if (isPlayerPos)
        {
            return "&" + "";
        }
        return myNodeType.getSymbol() + "";
    }
    
}
