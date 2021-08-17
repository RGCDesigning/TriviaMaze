package mazegame;
import java.io.Serializable;

public class GameState implements Serializable
{

    /**
     * Default serial version.
     */
    private static final long serialVersionUID = 1L;
    
    
    /**
     * The instance of the Game.
     */
    private Game myGameState;
    
    public GameState(final Game theGame)
    {
        myGameState = theGame;
    }
    
    /**
     * Gets the Game from the game state.
     * @return Returns the Game from the game state.
     */
    public Game getGameState()
    {
        return myGameState;
    }
    
}
