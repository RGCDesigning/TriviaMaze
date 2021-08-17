package mazegame;
import java.io.Serializable;

/**
 * True false question object.
 * @author Mylo
 * @version 7.18.21
 */

public class TFQuestion implements Question, Serializable
{

    /**
     * The default serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the Question.
     */
    private String myQuestion;
    
    /**
     * Stores the answer to the question as a boolean.
     */
    private boolean myAnswer; 
    
    public TFQuestion(final String theQuestion, final boolean theAnswer)
    {
        myQuestion = theQuestion;
        myAnswer = theAnswer;
    }
    
    /**
     * Verifies a correct answer.
     * @param theInput The answer to verify.
     * @return Returns whether or not the correct answer was input.
     */
    boolean verifyAnswer(final boolean theInput)
    {
        if (theInput == myAnswer)
        {
            return true;
        }
        return false;
    }
    
    @Override
    public String getQuestion() 
    {
        return myQuestion;
    }

    @Override
    public String getAnswer() 
    {
        if (myAnswer)
        {
            return "True";
        }
        return "False";
    }
    
    @Override
    public String toString()
    {
        return myQuestion + " \n\t" + myAnswer;
    }

}
