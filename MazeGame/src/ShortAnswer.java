import java.io.Serializable;

/**
 * Short answer question object.
 * @author Mylo
 * @version 7.18.21
 */

public class ShortAnswer implements Question, Serializable
{
    /**
     * The default serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the question.
     */
    private String myQuestion;
    
    /**
     * Stores the answer.
     */
    private String myAnswer;
    
    public ShortAnswer(final String theQuestion, final String theAnswer)
    {
        myQuestion = theQuestion;
        myAnswer = theAnswer;
    }
    
    /**
     * Verifies a correct answer.
     * @param theInput The answer to verify.
     * @return Returns whether or not the correct answer was input.
     */
    boolean verifyAnswer(final String theInput)
    {
        //one line return
        if (theInput.equals(myAnswer))
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
        return myAnswer;
    }
    
    @Override
    public String toString()
    {
        return myQuestion + " \n\t" + myAnswer;
    }
    
}
