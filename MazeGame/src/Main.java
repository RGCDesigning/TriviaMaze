import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Main
{
    public static long myTimeout = 50;
    
    public static int myCurrentlySelected = 0;
    
    /**
     * 0 = Map Open
     * 1 = Question Open
     * 2 = Menu Open
     */
    public static int myScreenType = 0;
    
    public static Question currentQuestion = null;
    
    public static void main(final String[] theArgs)
    {
        final Question testQuestion = new MultipleChoiceQuestion("This is a test question", new String[] {"These", "Are", "Test", "Answers"}, 3);
        final QuestionStack testStack = new QuestionStack(100);
        testStack.push(testQuestion);
        
        final Game testGame = new Game(4, 6, .5, testStack);
        
        startGame(testGame);
        
    }
    
    public static boolean startGame(final Game theGame)
    {
        
        theGame.printBoard();
        
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        
        try
        {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e)
        {
            e.printStackTrace();
        }
        
        GlobalScreen.addNativeKeyListener(new GlobalKeyListenerA());
        
        boolean playing = true;
        
        while (playing)
        {
                        
            if (GlobalKeyListenerA.readyToRead == false)
            {
                resetScreen();
                
                if (myScreenType == 0)
                {
                    Directions direction = GlobalKeyListenerA.nextDirection;
                    
                    System.out.println(theGame.isDoor(direction));
                    
                    boolean successfulMove = theGame.movePlayer(direction);
                    
                    System.out.println("Move Success: " + successfulMove);
                    
                    if (!successfulMove)
                    {
                        
                        System.out.println("Asking question");
                        currentQuestion = theGame.getQuestion();
//                        myScreenType = 1;
                    }
                                  
                    theGame.printBoard();
                    
                    GlobalKeyListenerA.readyToRead = true;
                    GlobalKeyListenerA.nextDirection = null;
                }
                
            }
            else
            {
                try
                {
                    Thread.currentThread().sleep(myTimeout);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
                        
        }
        
        return false;
    }
    
    public static String getUserTextInput()
    {
        Scanner reader = new Scanner(System.in);
        
        while (true)
        {
            String input = reader.nextLine();
            
            if (input != null && input.length() > 0)
            {
                reader.close();
                
                return input.toLowerCase().strip();
            }
            
        }
        
    }
    
    public static void resetScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void printMaze()
    {
        
    }
    
    public static void printMenu(final String[] theMenuItems)
    {
        if (myCurrentlySelected < 0)
        {
            myCurrentlySelected = theMenuItems.length - 1;
        }
        
        if (myCurrentlySelected > theMenuItems.length - 1)
        {
            myCurrentlySelected = 0;
        }
        
        for (int i = 0; i < theMenuItems.length; i++)
        {
            if (myCurrentlySelected == i)
            {
                System.out.print("\u25A0");
            }
            
            System.out.println("\t" + theMenuItems[i]);
            
        }
        
    }
    
    public static void printQuestion(final Question theQuestion)
    {
        
        System.out.println(theQuestion.getQuestion());
        
        if (theQuestion instanceof MultipleChoiceQuestion)
        {
            printMultiChoice((MultipleChoiceQuestion)theQuestion);
        }
        else if (theQuestion instanceof ShortAnswer)
        {
            printShortAnswer((ShortAnswer)theQuestion);
        }
        else if (theQuestion instanceof TFQuestion)
        {
            printTFQuestion((TFQuestion)theQuestion);
        }
    }
    
    private static void printMultiChoice(final MultipleChoiceQuestion theQuestion)
    {
        printMenu(theQuestion.getChoices());
    }
    
    private static void printShortAnswer(final ShortAnswer theQuestion)
    {
        System.out.print("->\t");
    }
    
    private static void printTFQuestion(final TFQuestion theQuestion)
    {
        printMenu(new String[] { "True", "False" });
    }
    
}
