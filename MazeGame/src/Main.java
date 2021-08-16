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
    
    public static MapNode currentInteraction = null;
    
    public static Coordinate lastPlayerPos = null;
    
    public static void main(final String[] theArgs)
    {
        final Question testQuestion = new MultipleChoiceQuestion("This is a test question", new String[] {"These", "Are", "Test", "Answers"}, 3);
        
        final Question t2 = new ShortAnswer("The answer is yes", "yes");
        
        final Question t3 = new TFQuestion("The answer is false", false);
        
        final QuestionStack testStack = new QuestionStack(100);
        testStack.push(testQuestion);
        testStack.push(t2);
        testStack.push(t3);
        
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
        
        boolean updateScreen = true;
        
        while (playing)
        {
            
            /**
             * Updates screen when necessary
             */
            if (updateScreen)
            {
                resetScreen();
                                
                if (myScreenType == 0)
                {
                    theGame.printBoard();
                    
                }
                else if (myScreenType == 1)
                {
                    theGame.printBoard();
                    System.out.println();
                    printQuestion(currentQuestion);
                    
                    if (currentQuestion instanceof ShortAnswer)
                    {
                        String answer = getUserTextInput();
                        
                        theGame.setDoor(currentInteraction.getCoordinate(), verifyAnswer(answer));
                        
//                        System.out.println("Setting pos to " + lastPlayerPos);
//                        
//                        theGame.setPlayerPos(lastPlayerPos);
                        
                        myScreenType = 0;
                        
                        continue;
                    }
                    
                    
                    
                }
                else if (myScreenType == 2)
                {
                    
                }
                
                updateScreen = false;
                
            }
            
            /**
             * Reads input when input waiting
             */
            if (GlobalKeyListenerA.readyToRead)
            {
                
                Directions direction = GlobalKeyListenerA.nextDirection;
                
                lastPlayerPos = theGame.getPlayerPos();
                
                if (myScreenType == 0)
                {
                    boolean successfulMove = theGame.movePlayer(direction);
                    
                    if (!successfulMove && theGame.isDoor(direction))
                    {
                        currentQuestion = theGame.getQuestion();
                        currentInteraction = theGame.getDoor(direction);
                        myScreenType = 1;
                    }
                    
                }
                else if (myScreenType == 1)
                {
                    if (direction == Directions.NORTH)
                    {
                        myCurrentlySelected--;
                    }
                    else if (direction == Directions.SOUTH)
                    {
                        myCurrentlySelected++;
                    }
                    else if (direction == Directions.EAST)
                    {              
                        
                        String answer = null;
                        
                        if (currentQuestion instanceof MultipleChoiceQuestion)
                        {
                            answer = ((MultipleChoiceQuestion) currentQuestion).getChoices()[myCurrentlySelected];
                        }
                        else if (currentQuestion instanceof TFQuestion)
                        {
                            if (myCurrentlySelected == 0)
                            {
                                answer = "true";
                            }
                            else
                            {
                                answer = "false";
                            }
                        }
                        else
                        {
                            continue;
                        }
                        
                        theGame.setDoor(currentInteraction.getCoordinate(), verifyAnswer(answer));
                        
                        myScreenType = 0;
                        
                    }
                }
                else if (myScreenType == 2)
                {
                    
                }
                
                updateScreen = true;
                GlobalKeyListenerA.readyToRead = false;
                GlobalKeyListenerA.nextDirection = null;
                
            }
            else
            {
                try
                {
                    Thread.currentThread().sleep(myTimeout);
                } 
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            
            
            
                        
        }
        
        return false;
    }
    
    public static boolean verifyAnswer(final String theInput)
    {
        
        if (currentQuestion.getAnswer().equalsIgnoreCase(theInput))
        {
            return true;
        }
        
        return false;
    }
    
    public static String getUserTextInput()
    {
        Scanner reader = new Scanner(System.in);
        
        while (true)
        {
            if (!reader.hasNextLine())
            {
                continue;
            }
            
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
        System.out.print("\t->");
    }
    
    private static void printTFQuestion(final TFQuestion theQuestion)
    {
        printMenu(new String[] { "True", "False" });
    }
    
}
