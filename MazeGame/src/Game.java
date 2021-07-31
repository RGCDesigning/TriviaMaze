import java.util.Scanner;

/**
 * The Game class.
 * @author r3mix
 * @version 7.30.21
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
    
    public Game(final int theRows, final int theCols, final double theDifficulty)
    {
        myMap = new Map(theRows, theCols);
        myPlayerX = 0;
        myPlayerY = 0;
    }
    
    public void setupGame()
    {
        
    }
    
    public void playGame()
    {
        boolean playing = true;
        
        final Scanner reader = new Scanner(System.in);
        
        while (playing)
        {
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
                    
                }
                else if (input[1].contains("down"))
                {
                    
                }
                else if (input[1].contains("left"))
                {
                    
                }
                else if (input[1].contains("right"))
                {
                    
                }
            }
            
        }
    }
}
