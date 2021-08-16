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
    
    public static QuestionStack loadFromDB(final String theDBLocation)
    {
        SQLiteDataSource ds = new SQLiteDataSource();
        
        ds.setUrl(String.format("jdbc:sqlite:%s", theDBLocation));
        
        QuestionStack stack = null;
        
        try
        {
            Connection con = ds.getConnection();
            
            Statement st = con.createStatement();
                        
            ResultSet count = st.executeQuery("SELECT COUNT(*) FROM Questions");
            
            int length = count.getInt(1);
            
            stack = new QuestionStack(length);
            
            String query = "SELECT * FROM Questions";
            
            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {                
                int questionType = rs.getInt("type");
                
                if (questionType == 0)
                {
                    String question = rs.getString("question").replace("&#039;", "'").replace("&quot;", "\"");
                    
                    String choicesString = rs.getString("choices").replace("&#039;", "'").replace("&quot;", "\"");
                    
                    String[] choices = choicesString.replace("&#039;", "'").replace("&quot;", "\"").split("~");
                    
                    int answer = rs.getInt("answer");
                    
                    MultipleChoiceQuestion tempQuestion = new MultipleChoiceQuestion(question, choices, answer);
                    
                    stack.push(tempQuestion);
                }
                
            }
            
            
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
           
        return stack;
        
    }
    
}
