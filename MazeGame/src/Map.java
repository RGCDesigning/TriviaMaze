import java.util.ArrayList;
import java.util.Collections;

public class Map 
{
    
    /**
     * 
     */
    private static final double DIFFICULTY = 0.30;
    
    /**
     * 
     */
    private static final double DOORCHANCE = 0.50;
    
    /**
     * 
     */
    private final int myRows;
    
    /**
     * 
     */
    private final int myCols;
    
    /**
     * 
     */
    private final int myRowBound;
    
    /**
     * 
     */
    private final int myColBound;
    
    /**
     * 
     */
    private final MapNode[][] myMap;
    
    public Map(final int theRowDimensions, final int theColumnsDimensions)
    {
        myRows = theRowDimensions;
        myCols = theColumnsDimensions;
        myRowBound = theRowDimensions * 2 + 1;
        myColBound = theColumnsDimensions * 2 + 1;
        myMap = new MapNode[myRowBound][myColBound];
        setupMap();
    }
    
    /**
     * Initializes the setup for the array.
     */
    private void setupMap()
    {
        setupInitialWalls();
        
        setupGeneratePath(1, 1);
        
        setupEntranceExit();
        
        setupClearRooms();
        
        setupSetDoors(DIFFICULTY);
        
        setupPunchHoles(DIFFICULTY, DOORCHANCE);
    }
    
    /**
     * Sets up the initial walls for the map.
     */
    private void setupInitialWalls()
    {
        for (int i = 0; i < myRowBound; i++)
        {
            for (int j = 0; j < myColBound; j++)
            {
                myMap[i][j] = new MapNode(MapNodeType.WALL);
            }
        }
    }
    
    /**
     * Sets up the entrance and exit.
     */
    private void setupEntranceExit()
    {
        myMap[1][1].setNodeType(MapNodeType.ENTRANCE);
        myMap[myRowBound - 2][myColBound - 2].setNodeType(MapNodeType.EXIT);
    }
    
    /**
     * Clears the rooms that cannot have a door. These can also be used to place non-door items.
     */
    private void setupClearRooms()
    {
        for (int i = 1; i < myRowBound - 1; i += 2)
        {
            for (int j = 1; j < myColBound - 1; j += 2)
            {
                if (myMap[i][j].getNodeType() == MapNodeType.VISITED)
                {
                    myMap[i][j].setNodeType(MapNodeType.HALLWAY);
                }
            }
        }
    }
    
    /**
     * Removes the visited spots and replaces them with either a door or a hallway depending on the
     * difficulty setting.
     * @param thePercentChance The chance that a door will be set.
     */
    private void setupSetDoors(final double thePercentChance)
    {
        for (int i = 0; i < myRowBound - 1; i += 1)
        {
            for (int j = 0; j < myColBound - 1; j += 1)
            {
                if (Math.random() <= thePercentChance)
                {
                    if (myMap[i][j].getNodeType() == MapNodeType.VISITED)
                    {
                        myMap[i][j].setNodeType(MapNodeType.DOOR);
                    }
                }
                else
                {
                    if (myMap[i][j].getNodeType() == MapNodeType.VISITED)
                    {
                        myMap[i][j].setNodeType(MapNodeType.HALLWAY);
                    }
                }
            }
        }
    }
    
    /**
     * Goes over non-corner spots on the map and has a percent chance to punch a hole through it.
     * @param thePercentChance The chance that a hole will be punched.
     * @param theDoorChance The chance that the hole will be filled with a door or hallway.
     */
    private void setupPunchHoles(final double thePercentChance, final double theDoorChance)
    {
        for (int i = 1; i < myRowBound - 1; i += 1)
        {
            for (int j = 1; j < myColBound - 1; j += 1)
            {
                // (i % 2 != 0 || j % 2 != 0) skips over corner pieces/connector walls
                if (myMap[i][j].getNodeType() == MapNodeType.WALL && (i % 2 != 0 || j % 2 != 0))
                {
                    if (Math.random() <= thePercentChance)
                    {
                        if (Math.random() <= theDoorChance)
                        {
                            myMap[i][j].setNodeType(MapNodeType.DOOR);
                        }
                        else
                        {
                            myMap[i][j].setNodeType(MapNodeType.HALLWAY);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Begins the recursive call to create a path to exit. Uses DFS.
     * @param theRow The starting row.
     * @param theCol The starting column.
     */
    private void setupGeneratePath(final int theRow, final int theCol)
    {
        setupExtendNode(theRow, theCol);
    }
    
    /**
     * The recursive method that extends paths in the array. Initialized by setupGeneratePath().
     * @param theRow The starting row.
     * @param theCol The starting column.
     */
    private void setupExtendNode(final int theRow, final int theCol)
    {
        final ArrayList<Directions> extendableDirections = new ArrayList<Directions>();
        
        if (setupTryPathNorth(theRow, theCol))
        {
            extendableDirections.add(Directions.NORTH);
        }
        
        if (setupTryPathSouth(theRow, theCol))
        {
            extendableDirections.add(Directions.SOUTH);
        }
        
        if (setupTryPathWest(theRow, theCol))
        {
            extendableDirections.add(Directions.WEST);
        }
        
        if (setupTryPathEast(theRow, theCol))
        {
            extendableDirections.add(Directions.EAST);
        }
        
        Collections.shuffle(extendableDirections);

        while (!extendableDirections.isEmpty())
        {
//            System.out.println("Extending to the " + extendableDirections.get(0));
            if (extendableDirections.get(0) == Directions.NORTH)
            {
                setupExtendNode(theRow - 2, theCol);
            }
            else if (extendableDirections.get(0) == Directions.SOUTH)
            {
                setupExtendNode(theRow + 2, theCol);
            }
            else if (extendableDirections.get(0) == Directions.WEST)
            {
                setupExtendNode(theRow, theCol - 2);
            }
            else if (extendableDirections.get(0) == Directions.EAST)
            {
                setupExtendNode(theRow, theCol + 2);
            }
            extendableDirections.remove(0);
        }
        
    }
    
    /**
     * Attempts to extend to the node immediately North.
     * @param theRow The row to extend from.
     * @param theCol The column to extend from.
     * @return Returns whether or not node successfully extends itself.
     */
    private boolean setupTryPathNorth(final int theRow, final int theCol)
    {
        if (theRow - 2 > 0 && myMap[theRow - 2][theCol].getNodeType() != MapNodeType.VISITED)
        {
            myMap[theRow - 1][theCol].setNodeType(MapNodeType.VISITED);
            myMap[theRow - 2][theCol].setNodeType(MapNodeType.VISITED);
            return true;
        }
        return false;
    }
    
    /**
     * Attempts to extend to the node immediately South.
     * @param theRow The row to extend from.
     * @param theCol The column to extend from.
     * @return Returns whether or not node successfully extends itself.
     */
    private boolean setupTryPathSouth(final int theRow, final int theCol)
    {
        if (theRow + 2 < myRowBound - 1 && myMap[theRow + 2][theCol].getNodeType() != MapNodeType.VISITED)
        {
            myMap[theRow + 1][theCol].setNodeType(MapNodeType.VISITED);
            myMap[theRow + 2][theCol].setNodeType(MapNodeType.VISITED);
            return true;
        }
        return false;
    }
    
    /**
     * Attempts to extend to the node immediately West.
     * @param theRow The row to extend from.
     * @param theCol The column to extend from.
     * @return Returns whether or not node successfully extends itself.
     */
    private boolean setupTryPathWest(final int theRow, final int theCol)
    {
        if (theCol - 2 > 0 && myMap[theRow][theCol - 2].getNodeType() == MapNodeType.WALL)
        {
            myMap[theRow][theCol - 1].setNodeType(MapNodeType.VISITED);
            myMap[theRow][theCol - 2].setNodeType(MapNodeType.VISITED);
            return true;
        }
        return false;
    }
    
    /**
     * Attempts to extend to the node immediately East.
     * @param theRow The row to extend from.
     * @param theCol The column to extend from.
     * @return Returns whether or not node successfully extends itself.
     */
    private boolean setupTryPathEast(final int theRow, final int theCol)
    {
        if (theCol + 2 < myColBound - 1 && myMap[theRow][theCol + 2].getNodeType() == MapNodeType.WALL)
        {
            myMap[theRow][theCol + 1].setNodeType(MapNodeType.VISITED);
            myMap[theRow][theCol + 2].setNodeType(MapNodeType.VISITED);
            return true;
        }
        return false;
    }
    
    /**
     * A method to print out the current array for testing.
     */
    public void debugPrint()
    {
        for (MapNode[] row : myMap)
        {
            for (MapNode node : row)
            {
                System.out.print(node + " ");
            }
            System.out.println();
        }
    }
    
}
