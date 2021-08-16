import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameController
{
    
    /**
     * Loads the previous state of a game and returns it.
     * @param theSaveLocation The location of the GameState object that has been serialized.
     * @return The preview state of the game.
     * @throws IOException Thrown when reading the file from disk has failed.
     * @throws ClassNotFoundException Thrown when parsing the file has failed.
     */
    public static GameState loadGameState(final String theSaveLocation) throws IOException, ClassNotFoundException
    {
        //Is this okay or should try/catch be used
        final FileInputStream file = new FileInputStream(theSaveLocation);
        final ObjectInputStream in = new ObjectInputStream(file);
        
        final GameState loadedGameState = (GameState) in.readObject();
        
        file.close();
        in.close();
        
        return loadedGameState;
        
    }
    
    /**
     * Saves the current game state.
     * @param theSaveLocation The location where the GameState will be serialized and saved.
     * @param theGameState The current state of the game that will be saved.
     * @throws IOException Thrown when there is an error writing to the given location.
     */
    public static void saveGameState(final String theSaveLocation, final GameState theGameState) throws IOException
    {
        final FileOutputStream file = new FileOutputStream(theSaveLocation);
        final ObjectOutputStream out = new ObjectOutputStream(file);
        
        out.writeObject(theGameState);
        
        file.close();
        out.close();
    }
    
}
