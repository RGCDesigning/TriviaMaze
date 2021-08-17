package mazetests;

import static org.junit.Assert.*;

import mazegame.Coordinate;
import org.junit.Before;
import org.junit.Test;


public class CoordinateTests
{
    
    /**
     * The test coordinate.
     */
    private Coordinate myTestCord;
    
    @Before
    public void setUp() throws Exception
    {
        myTestCord = new Coordinate(1, 1);
    }
    
    @Test
    public void testToString()
    {
        assertEquals("(1, 1)", myTestCord.toString(), "Coordinate not printing correctly.");
    }

}
