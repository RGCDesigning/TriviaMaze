import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class QuestionLoader 
{
    private QuestionLoader()
    {
        
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
                
            }
            
            
        } 
        catch (final SQLException e)
        {
            e.printStackTrace();
        }
           
        return stack;
        
    }
    
}
