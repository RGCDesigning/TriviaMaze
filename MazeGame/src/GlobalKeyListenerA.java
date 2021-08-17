import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListenerA implements NativeKeyListener
{

    /**
     * Whether or not a key is ready to be read.
     * If true, the Main class main method knows to read the key stored.
     */
    protected static boolean readyToRead;
    
    /**
     * Stores the direction that was input.
     */
    protected static Directions nextDirection;
    
    /**
     * Stores whether or not the game attempted to change states to a paused state.
     */
    protected static boolean gamePaused;
    
    @Override
    public void nativeKeyPressed(final NativeKeyEvent theKey)
    {
        
        if (!readyToRead && nextDirection == null)
        {
//            String key = k.getKeyText(k.getKeyCode());
            
            final int keyCode = theKey.getKeyCode();
            
            if (keyCode == NativeKeyEvent.VC_ESCAPE)
            {
                gamePaused = !gamePaused;
                readyToRead = true;
            }
            else if (keyCode == NativeKeyEvent.VC_LEFT)
            {
                nextDirection = Directions.WEST;
                readyToRead = true;
            }
            else if (keyCode == NativeKeyEvent.VC_RIGHT)
            {
                nextDirection = Directions.EAST;
                readyToRead = true;
            }
            else if (keyCode == NativeKeyEvent.VC_DOWN)
            {
                nextDirection = Directions.SOUTH;
                readyToRead = true;
            }
            else if (keyCode == NativeKeyEvent.VC_UP)
            {
                nextDirection = Directions.NORTH;
                readyToRead = true;
            }
//            else if (keyCode == NativeKeyEvent.VC_ENTER)
//            {
//                nextDirection = Directions.WEST;
//                readyToRead = true;
//            }
            
            
            
//            System.out.println(key);
            
        }
        
//        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(k.getKeyCode()));
        
    }

    @Override
    public void nativeKeyReleased(final NativeKeyEvent theKey)
    {
        
    }

    @Override
    public void nativeKeyTyped(final NativeKeyEvent theKey)
    {
        
    }
    
}
