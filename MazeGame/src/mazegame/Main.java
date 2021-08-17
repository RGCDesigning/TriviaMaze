package mazegame;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Main
{
    
    /**
     * The timeout time between each loop.
     */
    protected static long myTimeout = 50;
    
    /**
     * The currently selected value.
     * Used for menus.
     */
    protected static int myCurrentlySelected;
    
    /**
     * The currently selected DB.
     */
    protected static int myCurrentlySelectedDB;
    
    /**
     * The currently selected rows.
     */
    protected static int myCurrentlySelectedRows = 4;
    
    /**
     * The currently selected cols.
     */
    protected static int myCurrentlySelectedCols = 4;
    
    /**
     * Whether or not cheat mode is enabled.
     * Cheat mode highlights correct answer.
     */
    protected static boolean myCheatModeEnabled;
    
    /**
     * 0 = Map Open
     * 1 = Question Open
     * 2 = Menu Open
     * 3 = Pause Menu
     */
    protected static int myScreenType = 0;
    
    /**
     * The current question.
     */
    protected static Question currentQuestion;
    
    /**
     * The current node being interacted with.
     */
    protected static MapNode currentInteraction;
    
    /**
     * The last position of the player.
     */
    protected static Coordinate lastPlayerPos;
    
    /**
     * Controls the debug dialogue.
     */
    protected static boolean debugMode = true;
    
    /**
     * The text inside the debug dialoge.
     */
    protected static String debugText = "Debug Enabled";
    
    public static void main(final String[] theArgs)
    {
//        final Question testQuestion = new MultipleChoiceQuestion("This is a test question", new String[] {"These", "Are", "Test", "Answers"}, 3);
//        
//        final Question t2 = new ShortAnswer("The answer is yes", "yes");
//        
//        final Question t3 = new TFQuestion("The answer is false", false);
//        
//        final QuestionStack testStack = new QuestionStack(100);
//       
//        testStack.push(testQuestion);
//        testStack.push(t2);
//        testStack.push(t3);
        
        final QuestionStack testStack = QuestionLoader.loadFromDB("Test.db");
        
        mainMenu();
        
        final Game testGame = new Game(myCurrentlySelectedRows, myCurrentlySelectedCols, .4, testStack);
        
        startGame(testGame);
        
        System.exit(0);
        
    }
    
    /**
     * 
     * @return
     */
    public static boolean mainMenu()
    {
        /**
         * Adds native key listener and disables constant logs in console.
         */
        final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        
        try
        {
            GlobalScreen.registerNativeHook();
        } 
        catch (final NativeHookException e)
        {
            e.printStackTrace();
        }
        
        GlobalScreen.addNativeKeyListener(new GlobalKeyListenerA());
        
        final ArrayList<String> databases = QuestionLoader.getDBInDirectory(new File("."));
        
        boolean running = true;
        boolean updateScreen = true;
        
        while (running)
        {
            
            if (updateScreen)
            {
                resetScreen();
                
                System.out.println("\n\t- Trivia Maze Game -\n");
                printMenu(new String[] {"Maze Rows : " + myCurrentlySelectedRows,
                                        "Maze Cols : " + myCurrentlySelectedCols,
                                        "Cheats Enabled : " + myCheatModeEnabled,
                                        "Question DB : " + databases.get(myCurrentlySelectedDB),
                                        "Start Game"});
                
                updateScreen = false;
            }
            
            if (GlobalKeyListenerA.readyToRead)
            {
                final Directions input = GlobalKeyListenerA.nextDirection;
                
                if (input == Directions.NORTH)
                {
                    myCurrentlySelected--;
                }
                else if (input == Directions.SOUTH)
                {
                    myCurrentlySelected++;
                }
                else if (input == Directions.EAST)
                {
                    if (myCurrentlySelected == 0 && myCurrentlySelectedRows < 100) //Rows
                    {
                        myCurrentlySelectedRows++;
                    }
                    else if (myCurrentlySelected == 1 && myCurrentlySelectedCols < 100) //Columns
                    {
                        myCurrentlySelectedCols++;
                    }
                    else if (myCurrentlySelected == 2) //Cheats
                    {
                        myCheatModeEnabled = !myCheatModeEnabled;
                    }
                    else if (myCurrentlySelected == 3 && myCurrentlySelectedDB < databases.size() - 1) //DB
                    {
                        myCurrentlySelectedDB++;
                    }
                    else if (myCurrentlySelected == 4) //Start Game
                    {
                        running = false;
                        break;
                    }
                }
                else if (input == Directions.WEST)
                {
                    if (myCurrentlySelected == 0 && myCurrentlySelectedRows > 4) //Rows
                    {
                        myCurrentlySelectedRows--;
                    }
                    else if (myCurrentlySelected == 1 && myCurrentlySelectedCols > 4) //Columns
                    {
                        myCurrentlySelectedCols--;
                    }
                    else if (myCurrentlySelected == 2) //Cheats
                    {
                        myCheatModeEnabled = !myCheatModeEnabled;
                    }
                    else if (myCurrentlySelected == 3 && myCurrentlySelectedDB > 0) //DB
                    {
                        myCurrentlySelectedDB--;
                    }
                }
                
                updateScreen = true;
                clearKeyListener();
                
            }
            else
            {
                try
                {
                    Thread.sleep(myTimeout);
                } 
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            
        }
        
        resetInit();
        clearKeyListener();
        
        return true;
        
    }
    
    /**
     * Main Game Loop
     * @param theGame The game to run.
     * @return Returns whether or not to continue;
     */
    public static boolean startGame(final Game theGame)
    {
        
        /**
         * Initializes game with board print.
         */
        theGame.printBoard();
        
        
        /**
         * Adds native key listener and disables constant logs in console.
         */
        final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        
        try
        {
            GlobalScreen.registerNativeHook();
        } 
        catch (final NativeHookException e)
        {
            e.printStackTrace();
        }
        
        GlobalScreen.addNativeKeyListener(new GlobalKeyListenerA());
        
        boolean playing = true;
        
        boolean updateScreen = true;
        
        while (playing)
        {
            
            /**
             * Checks if player has won.
             */
            if (theGame.playerOnExit())
            {
                resetScreen();
                
                theGame.printBoard();
                
                System.out.println("YOU WIN!");
                playing = false;
                continue;
            }
            
            /**
             * Updates screen when necessary
             */
            if (updateScreen)
            {
                
                /**
                 * Wipes the screen.
                 */
                resetScreen();
                
                if (debugMode)
                {
                    System.out.println("[ " + debugText + " ]");
                }
                                
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
                        final String answer = getUserTextInput();
                        
                        theGame.setDoor(currentInteraction.getCoordinate(), verifyAnswer(answer));
                                                
                        debugText = "Setting pos to " + lastPlayerPos;
                        
                        theGame.setPlayerPos(lastPlayerPos);
                        
                        myScreenType = 0;
                        
                        continue;
                    }
                    
                    
                    
                }
                else if (myScreenType == 2)
                {
                    
                }
                else if (myScreenType == 3)
                {
                    System.out.println("\n\t- Game Paused -\n");
                    
                    printMenu(new String[] {"Save Game", "Load Game", "Continue Game", "Quit Game"});
                }
                
                updateScreen = false;
                
            }
            
            /**
             * Reads input when input waiting
             */
            if (GlobalKeyListenerA.readyToRead)
            {
                
                if (myScreenType == 0 && GlobalKeyListenerA.gamePaused)
                {
                    myScreenType = 3;
                }
                else if (myScreenType == 3 && GlobalKeyListenerA.gamePaused == false)
                {
                    myScreenType = 0;
                    
                    updateScreen = true;
                    clearKeyListener();
                    continue;
                }
                else if ((myScreenType != 3 && myScreenType != 0) && GlobalKeyListenerA.gamePaused)
                {
                    GlobalKeyListenerA.gamePaused = false;
                }
                
                final Directions direction = GlobalKeyListenerA.nextDirection;
                
                lastPlayerPos = theGame.getPlayerPos();
                
                if (myScreenType == 0)
                {
                    final boolean successfulMove = theGame.movePlayer(direction);
                    
                    /**
                     * If the player attempts to move passed a door, a question will be asked.
                     */
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
                            /**
                             * Gets answer from multiple choice and verifies.
                             */
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
                        
                        
                        /**
                         * Sets the door status based on whether or not the answer is correct.
                         */
                        theGame.setDoor(currentInteraction.getCoordinate(), verifyAnswer(answer));
                        
                        myScreenType = 0;
                        
                    }
                }
                else if (myScreenType == 2)
                {
                    if (direction == Directions.NORTH)
                    {
                        myCurrentlySelected--;
                    }
                    else if (direction == Directions.SOUTH)
                    {
                        myCurrentlySelected++;
                    }
                }
                else if (myScreenType == 3)
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
                        /**
                         * Save Game
                         */
                        if (myCurrentlySelected == 0)
                        {
                            saveGame(theGame);
                            GlobalKeyListenerA.gamePaused = false;
                            
                            debugText = "Save Successful";
                            
                            updateScreen = true;
                            clearKeyListener();
                        }
                        /**
                         * Load Game
                         */
                        else if (myCurrentlySelected == 1)
                        {
                            Game loadedGame = loadGame();
                            
                            if (loadedGame == null)
                            {
                                debugText = "Load Failed!";
                                continue;
                            }
                            
                            playing = false;
                            
                            debugText = "Load Successful";
                            
                            resetInit();
                            
                            System.out.println("Loading Game Dude");
                            
                            startGame(loadedGame);
                            
                            break;
                            
                        }
                        /**
                         * Continue Game
                         */
                        else if (myCurrentlySelected == 2)
                        {
                            GlobalKeyListenerA.gamePaused = false;
                            
                            updateScreen = true;
                            clearKeyListener();
                            
                        }
                        /**
                         * Quit Game
                         */
                        else if (myCurrentlySelected == 3)
                        {
                            System.exit(0);
                        }
                    }
                    
                    debugText = "Paused Game";
                    
                }
                
                updateScreen = true;
                clearKeyListener();
                
            }
            else
            {
                try
                {
                    Thread.currentThread().sleep(myTimeout);
                } 
                catch (final InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            
            
            
                        
        }
        
        return false;
    }
    
    /**
     * Resets the game to its initial conditions.
     * This method does not reset the map.
     */
    public static void resetInit()
    {
        clearKeyListener();
        
        GlobalKeyListenerA.gamePaused = false;
        
        myCurrentlySelected = 0;
        
        myScreenType = 0;
        
        currentQuestion = null;
        
        currentInteraction = null;
        
        lastPlayerPos = null;
        
        debugText = "Load Successful";
    }
    
    /**
     * Triggers a load from default save name.
     * @return Returns the Game from the loaded state.
     */
    public static Game loadGame()
    {
        
        Game game = null;
        
        try
        {
            final GameState state = GameController.loadGameState("save.mz");
            
            game = state.getGameState();
        } 
        catch (final ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
        
        return game;
        
    }
    
    /**
     * Triggers a save game.
     * @param theGame The game to save.
     * @return Returns whether or not the save was successful.
     */
    public static boolean saveGame(final Game theGame)
    {
        final GameState game = new GameState(theGame);
        
        try
        {
            GameController.saveGameState("save.mz", game);
        } 
        catch (final IOException e)
        {
            return false;
        }
        
        return true;
        
    }
    
    /**
     * Verifies the answer using currentQuestion and theInput String.
     * @param theInput The String to test.
     * @return Returns whether or not theInput was the correct answer.
     */
    public static boolean verifyAnswer(final String theInput)
    {
        
        if (currentQuestion.getAnswer().equalsIgnoreCase(theInput))
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * Interrupts to get user input.
     * @return Returns the String that was input.
     */
    public static String getUserTextInput()
    {
        final Scanner reader = new Scanner(System.in);
        
        while (true)
        {
            if (!reader.hasNextLine())
            {
                continue;
            }
            
            final String input = reader.nextLine();
            
            if (input != null && input.length() > 0)
            {
                reader.close();
                
                return input.toLowerCase().strip();
            }
            
        }
        
    }
    
    /**
     * Clears the key listener.
     * This method does not fully reset the key listener.
     */
    public static void clearKeyListener()
    {
        GlobalKeyListenerA.readyToRead = false;
        GlobalKeyListenerA.nextDirection = null;
    }
    
    /**
     * Resets the terminal screen.
     */
    public static void resetScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * Base method for printing a menu.
     * This method does not control verification.
     * @param theMenuItems The items to print.
     */
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
    
    /**
     * Print a question menu.
     * @param theQuestion The question to ask.
     */
    public static void printQuestion(final Question theQuestion)
    {
        
        System.out.println(theQuestion.getQuestion());
        
        if (theQuestion instanceof MultipleChoiceQuestion)
        {
            printMultiChoice((MultipleChoiceQuestion) theQuestion);
        }
        else if (theQuestion instanceof ShortAnswer)
        {
            printShortAnswer((ShortAnswer) theQuestion);
        }
        else if (theQuestion instanceof TFQuestion)
        {
            printTFQuestion((TFQuestion) theQuestion);
        }
    }
    
    /**
     * Print the menu for a multiple choice question.
     * @param theQuestion The question to ask.
     */
    private static void printMultiChoice(final MultipleChoiceQuestion theQuestion)
    {
        printMenu(theQuestion.getChoices());
    }
    
    /**
     * Prints menu for a short answer question.
     * @param theQuestion The question to ask.
     */
    private static void printShortAnswer(final ShortAnswer theQuestion)
    {
        System.out.print("\t->");
    }
    
    /**
     * Print the menu for a true false question.
     * @param theQuestion The Question to ask.
     */
    private static void printTFQuestion(final TFQuestion theQuestion)
    {
        printMenu(new String[] {"True", "False"});
    }
    
}
