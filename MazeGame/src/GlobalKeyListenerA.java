import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListenerA implements NativeKeyListener
{

    public static boolean readyToRead = false;
    
    public static Directions nextDirection = null;
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent k)
    {
        
        if (!readyToRead && nextDirection == null)
        {
//            String key = k.getKeyText(k.getKeyCode());
            
            int keyCode = k.getKeyCode();
            
            if (keyCode == NativeKeyEvent.VC_LEFT)
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
    public void nativeKeyReleased(NativeKeyEvent k)
    {
//        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(k.getKeyCode()));
        
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent k)
    {
        // TODO Auto-generated method stub
        
    }
    
}
