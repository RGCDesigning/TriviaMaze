
public class MapNode 
{
    
    /**
     * The type of MapNode.
     */
    private MapNodeType myNodeType;
    
    MapNode(final MapNodeType theNodeType)
    {
        myNodeType = theNodeType;
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
    
    public String toString()
    {
        return myNodeType.getSymbol() + "";
    }
    
}
