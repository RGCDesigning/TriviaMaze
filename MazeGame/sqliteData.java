import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.sqlite.SQLiteDataSource;

public class sqliteData {
	public static ArrayList<Question> toArrayList() {
		ArrayList<Question> questions = new ArrayList<Question>();
        SQLiteDataSource ds = null;

        //establish connection (creates db file if it does not exist :-)
        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:maze.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        String query = "SELECT * FROM questions";
        

        

        try ( Connection conn = ds.getConnection();
              Statement stmt = conn.createStatement(); ) {
            
            ResultSet rs = stmt.executeQuery(query);
            
            //walk through each 'row' of results, grab data by column/field name
            // and print it
            while ( rs.next() ) {
                String question = rs.getString( "QUESTION" );
                String opt1 = rs.getString( "CHOICE1" );
                String opt2 = rs.getString( "CHOICE2" );
                String opt3 = rs.getString( "CHOICE3" );
                String opt4 = rs.getString( "CHOICE4" );             
                String answer = rs.getString( "ANSWER" );

                System.out.println( "Result: Question = " + question + opt1 + opt2 + opt3 + opt4 +
                    ", Answer = " + answer );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
		return questions;
	}


}
