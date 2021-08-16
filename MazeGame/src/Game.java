import java.util.Scanner;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * The Game class.
 * @author r3mix
 * @version 8.14.21
 */

public class Game
{
    
    /**
     * Instance of the game's map.
     */
    private Map myMap;
     
    /**
     * Instance of the game's question stack.
     */
    private QuestionStack myQuestions;
    
    /**
     * The player's current X position.
     */
    private int myPlayerX;
    
    /**
     * The player's current Y position.
     */
    private int myPlayerY;
    
    public Game(final int theRows, final int theCols, final double theDifficulty, final QuestionStack theQuestions)
    {
        myQuestions = theQuestions;
        myMap = new Map(theRows, theCols);
        myPlayerX = 1;
        myPlayerY = 1;
    }
    
    public void setDoor(final Coordinate theDoor, final boolean isUnlocked)
    {
        if (isUnlocked)
        {
            myMap.unlockDoor(theDoor);
        }
        else
        {
            myMap.lockDoor(theDoor);
        }
    }
    
    public MapNode getDoor(final Directions theDirections)
    {
        
        if (myMap.getPassage(getPlayerPos(), theDirections).getNodeType() == MapNodeType.DOOR)
        {
            return myMap.getPassage(getPlayerPos(), theDirections);
        }
        
        return null;
    }
    
    public void setPlayerPos(final Coordinate theCord)
    {
        myMap.movePlayerToRoom(theCord);
    }
    
    public Coordinate getPlayerPos()
    {
        return myMap.getPlayerPos().getCoordinate();
    }
    
    public Question getQuestion()
    {
        return myQuestions.pop();
    }
    
    public boolean movePlayer(final Directions theDirections)
    {
        System.out.println("Moving player - " + theDirections);
        return myMap.movePlayer(theDirections);
    }
    
    public boolean isDoor(final Directions theDirections)
    {
        System.out.println(myMap.getPassage(getPlayerPos(), theDirections));
        return myMap.getPassage(getPlayerPos(), theDirections).getNodeType() == MapNodeType.DOOR;
    }
    
    public void printBoard()
    {
        myMap.debugPrint();
    }
    
    public void playGame()
    {
        boolean playing = true;
        
        final Scanner reader = new Scanner(System.in);
        
        while (playing)
        {
            
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
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
    }

}
