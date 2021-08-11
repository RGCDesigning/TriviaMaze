import java.io.Serializable;

public class GameState implements Serializable
{

    /**
     * 
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
    
}
