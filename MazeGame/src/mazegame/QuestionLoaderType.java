package mazegame;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.sqlite.SQLiteDataSource;

public class QuestionLoaderType 
{
	/**
	 * private empty contractor
	 */
    private QuestionLoaderType()
    {
    // do nothing
    }
    
    /**
     * Gets all DB files from directory.
     * @param theDirectory The directory to look in.
     * @return Returns all DB files.
     */
    public static ArrayList<String> getDBInDirectory(final File theDirectory)
    {
        
        final ArrayList<String> paths = new ArrayList<String>(); 
                
        for (final File f : theDirectory.listFiles())
        {
            if (f.isDirectory())
            {
                paths.addAll(getDBInDirectory(f));
            }
            else
            {
                final int lastIndex = f.toString().lastIndexOf('.');
                
                if (lastIndex > 0 && f.toString().substring(lastIndex + 1).equals("db"))
                {
                    paths.add(f.toString());
                }
            }
        }
        
        return paths;
        
    }
    
    /**
     * Loads the QuestionStack from a given SQLite database.
     * @param theDBLocation The location of the database to load.
     * @return Returns the QuestionStack created from the loaded database.
     */
    public static QuestionStack loadFromDB(final String theDBLocation)
    {
        final SQLiteDataSource ds = new SQLiteDataSource();
        
        ds.setUrl(String.format("jdbc:sqlite:%s", theDBLocation));
        
        QuestionStack stack = null;
        
        try
        {
            final Connection con = ds.getConnection();
            
            final Statement st = con.createStatement();
                        
            final ResultSet count = st.executeQuery("SELECT COUNT(*) FROM Questions");
            
            final int length = count.getInt(1);
            
            stack = new QuestionStack(length);
            
            final String query = "SELECT * FROM Questions";
            
            final ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {                
                final int questionType = rs.getInt("type");
                
                if (questionType == 0)
                {
                    final String question = rs.getString("question").replace("&#039;", "'").replace("&quot;", "\"");
                    
                    final String choicesString = rs.getString("choices").replace("&#039;", "'").replace("&quot;", "\"");
                    
                    final String[] choices = choicesString.replace("&#039;", "'").replace("&quot;", "\"").split("~");
                    
                    final int answer = rs.getInt("answer");
                    
                    final MultipleChoiceQuestion tempQuestion = new MultipleChoiceQuestion(question, choices, answer);
                    
                    stack.push(tempQuestion);
                }
                
                if (questionType == 1) {
                	String question = rs.getString("question").replace("&#039;", "'").replace("&quot;", "\"");

                    boolean answer = rs.getBoolean("answer");
                    
                    TFQuestion tempQuestion = new TFQuestion(question, answer);
                    
                    stack.push(tempQuestion);
                	
                }
                if (questionType == 2) {
                	String question = rs.getString("question").replace("&#039;", "'").replace("&quot;", "\"");

                    String answer = rs.getString("answer");
                    
                    ShortAnswer tempQuestion = new ShortAnswer(question, answer.toLowerCase());
                    
                    stack.push(tempQuestion);
                	
                }
                
            }
            
            
        } 
        catch (final SQLException e)
        {
            e.printStackTrace();
        }
           
        return stack;
        
    }
    
}
