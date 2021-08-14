import java.util.ArrayList;
import java.util.Collections;

/**
 * The Map class.
 * @author r3mix
 * @version 8.14.21
 */

public class Map 
{
    
    /**
     * The difficulty of the game. Controls the spawn rate of doors.
     */
    private static final double DIFFICULTY = 0.4;
    
    /**
     * The percent chance that a door will spawn when a wall is opened.
     */
    private static final double DOORCHANCE = 0.5;
    
    /**
     * The rows in the map.
     */
    private final int myRows;
    
    /**
     * The columns in the map.
     */
    private final int myCols;
    
    /**
     * The actual rows of the map's array.
     */
    private final int myRowBound;
    
    /**
     * The actual columns of the map's array.
     */
    private final int myColBound;
    
    /**
     * The 2D array of MapNodes that represents the map itself.
     */
    private final MapNode[][] myMap;
    
    /**
     * Stores the current node the player is in.
     */
    private MapNode playerPos;
    
    public Map(final int theRowDimensions, final int theColumnsDimensions)
    {
        myRows = theRowDimensions;
        myCols = theColumnsDimensions;
        myRowBound = theRowDimensions * 2 + 1;
        myColBound = theColumnsDimensions * 2 + 1;
        myMap = new MapNode[myRowBound][myColBound];
        setupMap();
        myMap[1][1].setPlayerPresent(true);
        playerPos = myMap[1][1];
    }
    
    /**
     * 
     * @param theRow
     * @param theCol
     */
    public void translatePos(final int theRow, final int theCol)
    {
        System.out.println((theRow * 2 + 1) + ", " + (theCol * 2 + 1));
    }
    
    /**
     * 
     * @param theRow
     * @param theCol
     */
    public void unlockDoor(final int theRow, final int theCol)
    {
        System.out.println("Attempting unlock at " + new Coordinate(theRow, theCol));
        
        if (myMap[theRow][theCol].getNodeType() == MapNodeType.DOOR)
        {
            System.out.println("Unlocked!");
            myMap[theRow][theCol].setNodeType(MapNodeType.OPENED_DOOR);
        }
        
    }
    
    public void unlockDoor(final Directions theDirection)
    {
        if (theDirection == Directions.NORTH)
        {
            unlockDoor(playerPos.getCoordinate().myX - 1, playerPos.getCoordinate().myY);
        }
        else if (theDirection == Directions.SOUTH)
        {
            unlockDoor(playerPos.getCoordinate().myX + 1, playerPos.getCoordinate().myY);
        }
        else if (theDirection == Directions.WEST)
        {
            unlockDoor(playerPos.getCoordinate().myX, playerPos.getCoordinate().myY - 1);
        }
        else if (theDirection == Directions.EAST)
        {
            unlockDoor(playerPos.getCoordinate().myX, playerPos.getCoordinate().myY + 1);
        }
    }
    
    
    
    /**
     * Locks the door at a given position.
     * @param theRow The row to lock.
     * @param theCol The column to lock.
     */
    public void lockDoor(final int theRow, final int theCol)
    {
        System.out.println("Attempting lock at " + new Coordinate(theRow, theCol));
        
        if (myMap[theRow][theCol].getNodeType() == MapNodeType.DOOR)
        {
            System.out.println("Locked!");
            myMap[theRow][theCol].setNodeType(MapNodeType.WALL);
        }
        
    }
    
    /**
     * Wrapper for lockDoor method.
     * @param theCoordinate The coordinate to lock.
     */
    public void lockDoor(final Coordinate theCoordinate)
    {
        lockDoor(theCoordinate.myX, theCoordinate.myY);
    }
    
    /**
     * Attemps to move the player in a specified direction/
     * @param theDirection The direction of the move.
     * @return Whether or not the move was successful.
     */
    public boolean movePlayer(final Directions theDirection)
    {
        MapNode passage = getPassage(playerPos.getCoordinate(), theDirection);
        
        if (passage == null || passage.getNodeType() == MapNodeType.DOOR || passage.getNodeType() == MapNodeType.WALL)
        {
            return false;
        }
        
        MapNode nextPos = getRoom(playerPos.getCoordinate(), theDirection);
        
        playerPos.setPlayerPresent(false);
        
        nextPos.setPlayerPresent(true);
        
        playerPos = nextPos;
        
        return true;
        
    }
    
    public MapNode getRoom(final int theRow, final int theCol, final Directions theDirection)
    {
        if (theRow < 1 || theRow > myRowBound - 1 || theCol < 1 || theCol > myColBound - 1)
        {
            return null;
        }
        
        if (theDirection == Directions.NORTH && theRow > 0)
        {
            return myMap[theRow - 2][theCol];
        }
        else if (theDirection == Directions.SOUTH && theRow < myRowBound)
        {
            return myMap[theRow + 2][theCol];
        }
        else if (theDirection == Directions.WEST && theCol > 0)
        {
            return myMap[theRow][theCol - 2];
        }
        else if (theDirection == Directions.EAST && theCol < myColBound)
        {
            return myMap[theRow][theCol + 2];
        }
        
        return null;
    }
    
    public MapNode getRoom(final Coordinate theCoordinate, final Directions theDirection)
    {
        return getRoom(theCoordinate.myX, theCoordinate.myY, theDirection);
    }
    
    /**
     * Gets the passage that is in theDirection.
     * @param theRow The current row to check.
     * @param theCol The current column to check.
     * @param theDirection The direction to check in.
     * @return Returns the node at that position.
     */
    public MapNode getPassage(final int theRow, final int theCol, final Directions theDirection)
    {
        if (theRow < 0 || theRow > myRowBound || theCol < 0 || theCol > myColBound)
        {
            return null;
        }
        
        if (theDirection == Directions.NORTH && theRow > 0)
        {
            return myMap[theRow - 1][theCol];
        }
        else if (theDirection == Directions.SOUTH && theRow < myRowBound)
        {
            return myMap[theRow + 1][theCol];
        }
        else if (theDirection == Directions.WEST && theCol > 0)
        {
            return myMap[theRow][theCol - 1];
        }
        else if (theDirection == Directions.EAST && theCol < myColBound)
        {
            return myMap[theRow][theCol + 1];
        }
        
        return null;
        
    }
    
    /**
     * Gets the passage that is in theDirection.
     * @param theCoordinate The row and column to check from.
     * @param theDirection The direction to check in.
     * @return Returns the node at that position.
     */
    public MapNode getPassage(final Coordinate theCoordinate, final Directions theDirection)
    {
        return getPassage(theCoordinate.myX, theCoordinate.myY, theDirection);
    }
    
    
    /**
     * Initializes the setup for the array.
     */
    private void setupMap()
    {
        //Sets up map, pads sides
        setupInitialWalls();
        
        //Generates a path from top left to bottom right using DFS
        setupGeneratePath(1, 1);
        
        //Sets the entrance and exit. Can be randomized for added difficulty
        setupEntranceExit();
        
        //Clears out the PLAY ACCESSIBLE spots. These are spots that can be used for items/powerups if time permits.
        setupClearRooms();
        
        //Follows path from DFS and randomly sets doors or walkways depending on difficulty
        setupSetDoors(DIFFICULTY);
        
        //Runs through each non-corner and punches a hole depending on difficulty and door chance
        //Difficulty decides if the wall will be replaced -> Lowers difficulty
        //Door chance decides what it will be replaced with -> Raises OR lowers difficulty
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
                myMap[i][j] = new MapNode(MapNodeType.WALL, new Coordinate(i, j));
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
        //Is this long method okay?
        
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
    
    /**
     * Gets the player position as a node.
     * @return Returns the player position as a node.
     */
    public MapNode getPlayerPos()
    {
        return playerPos;
    }
    
    /**
     * Gets the rows for the map.
     * @return The rows for the map.
     */
    public int getMyRows()
    {
        return myRows;
    }
    
    /**
     * Sets the rows for the map.
     * @return The columns for the map.
     */
    public int getMyCols()
    {
        return myCols;
    }
    
}
