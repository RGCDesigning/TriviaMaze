
public enum MapNodeType
{
    /**
     * The types of passage ways in the MapNode.
     */
    HALLWAY(' '), WALL('#'), DOOR('D'), OPENED_DOOR('d'), ENTRANCE('O'), EXIT('X'), VISITED('V');
    
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
