package mazetests;

import static org.junit.Assert.*;

import mazegame.Game;
import org.junit.Before;
import org.junit.Test;

public class GameTests
{
    
    /**
     * The game to test.
     */
    private Game myGameOne;
    
    @Before
    public void setUp() throws Exception
    {
        myGameOne = new Game(100, 100, 0.5, null);
    }

}
