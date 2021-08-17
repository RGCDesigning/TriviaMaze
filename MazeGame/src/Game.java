import java.io.Serializable;
import java.util.Scanner;


/**
 * The Game class.
 * @author r3mix
 * @version 8.14.21
 */

public class Game implements Serializable
{
    
    /**
     * The default serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instance of the game's map.
     */
    private Map myMap;
     
    /**
     * Instance of the game's question stack.
     */
    private QuestionStack myQuestions;
    
    
    public Game(final int theRows, final int theCols, final double theDifficulty, final QuestionStack theQuestions)
    {
        myQuestions = theQuestions;
        myMap = new Map(theRows, theCols);
//        myPlayerX = 1;
//        myPlayerY = 1;
    }
    
    /**
     * Sets the door at a given coordinate to be unlocked or locked.
     * @param theDoor The door to modify.
     * @param theUnlockedStatus Whether or not door will be unlocked.
     */
    public void setDoor(final Coordinate theDoor, final boolean theUnlockedStatus)
    {
        if (theUnlockedStatus)
        {
            myMap.unlockDoor(theDoor);
        }
        else
        {
            myMap.lockDoor(theDoor);
        }
    }
    
    /**
     * Gets the door from a given direction based on player position.
     * @param theDirections
     * @return Returns the MapNode in that direction.
     */
    public MapNode getDoor(final Directions theDirections)
    {
        
        if (myMap.getPassage(getPlayerPos(), theDirections).getNodeType() == MapNodeType.DOOR)
        {
            return myMap.getPassage(getPlayerPos(), theDirections);
        }
        
        return null;
    }
    
    /**
     * Checks if player is standing on an exit.
     * @return Returns if the player is on the exit.
     */
    public boolean playerOnExit()
    {
        return myMap.getRoom(getPlayerPos()).getNodeType() == MapNodeType.EXIT;
    }
    
    /**
     * Sets the player position to given coordinates.
     * @param theCord The coordinate to set the player position to.
     */
    public void setPlayerPos(final Coordinate theCord)
    {
        myMap.movePlayerToRoom(theCord);
    }
    
    /**
     * Gets the player's current position.
     * @return Returns the coordinates of the player position.
     */
    public Coordinate getPlayerPos()
    {
        return myMap.getPlayerPos().getCoordinate();
    }
    
    /**
     * Gets the next question from the question stack.
     * @return Returns the top question.
     */
    public Question getQuestion()
    {
        return myQuestions.pop();
    }
    
    /**
     * Move player in given direction.
     * @param theDirections The direction to move the player in.
     * @return Returns whether or not the move was successful.
     */
    public boolean movePlayer(final Directions theDirections)
    {
//        System.out.println("Moving player - " + theDirections);
        return myMap.movePlayer(theDirections);
    }
    
    /**
     * Checks whether or not a door is in a given passage based on direction and player position.
     * @param theDirections The direction to check.
     * @return Returns whether or not the passage in the direction is a door.
     */
    public boolean isDoor(final Directions theDirections)
    {
        System.out.println(myMap.getPassage(getPlayerPos(), theDirections));
        return myMap.getPassage(getPlayerPos(), theDirections).getNodeType() == MapNodeType.DOOR;
    }
    
    /**
     * Prints the board.
     */
    public void printBoard()
    {
        myMap.debugPrint();
    }
    
    /**
     * Main Game Loop
     * OLD
     */
    public void playGame()
    {
        boolean playing = true;
        
        final Scanner reader = new Scanner(System.in);
        
        while (playing)
        {
            
            /**
             * This line will clear the command prompt.
             * Requires terminal to be able to read ANSI characters.
             */
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            /**
             * Prints out the board
             */
            myMap.debugPrint();
            
            System.out.println("Input move:");
            
            final String[] input = reader.nextLine().split(" ");

            for (int i = 1; i < input.length; i++)
            {
                input[i] = input[i].toLowerCase();
            }
            
            final String command = input[0].toLowerCase();
            
            final int modifiers = input.length - 1;
            
            if (command.contains("quit"))
            {
                playing = false;
            }
            else if (command.contains("move") && modifiers == 1)
            {
                if (input[1].contains("up"))
                {
                    myMap.movePlayer(Directions.NORTH);
                }
                else if (input[1].contains("down"))
                {
                    myMap.movePlayer(Directions.SOUTH);
                }
                else if (input[1].contains("left"))
                {
                    myMap.movePlayer(Directions.WEST);
                }
                else if (input[1].contains("right"))
                {
                    myMap.movePlayer(Directions.EAST);
                }
            }
            else if (command.contains("unlock") && modifiers == 1)
            {
                if (input[1].contains("up"))
                {
                    myMap.unlockDoor(Directions.NORTH);
                }
                else if (input[1].contains("down"))
                {
                    myMap.unlockDoor(Directions.SOUTH);
                }
                else if (input[1].contains("left"))
                {
                    myMap.unlockDoor(Directions.WEST);
                }
                else if (input[1].contains("right"))
                {
                    myMap.unlockDoor(Directions.EAST);
                }
            }
            else
            {
                System.out.println("Invalid move!");
            }
            
        }
        
        reader.close();
        
    }

}
