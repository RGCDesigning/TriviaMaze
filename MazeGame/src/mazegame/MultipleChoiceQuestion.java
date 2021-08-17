package mazegame;
import java.io.Serializable;

/**
 * Multiple choice question object.
 * @author Mylo
 * @version 7.18.21
 */

public class MultipleChoiceQuestion implements Question, Serializable
{
    
    /**
     * The default serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the choices to the question.
     */
    private String[] myChoices;
    
    /**
     * Stores the Question.
     */
    private final String myQuestion;
    
    /**
     * Stores the answer to the question as an integer.
     */
    private final int myAnswer; 
    
    public MultipleChoiceQuestion(final String theQuestion, final String[] theChoices, final int theAnswer)
    {
        myQuestion = theQuestion;
        myChoices = theChoices;
        myAnswer = theAnswer;
    }
    
    /**
     * Verifies a correct answer.
     * @param theInput The answer to verify.
     * @return Returns whether or not the correct answer was input.
     */
    public boolean verifyAnswer(final int theInput)
    {
        //one line return
        if (theInput == myAnswer)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Returns the multiple choices for Question.
     * @return Returns the choices.
     */
    public String[] getChoices()
    {
        return myChoices.clone();
    }

    @Override
    public String getQuestion() 
    {
        return myQuestion;
    }

    @Override
    public String getAnswer() 
    {
        return myChoices[myAnswer];
    }
    
    @Override
    public String toString()
    {
        return myQuestion + " \n\t" + myChoices[myAnswer];
    }

}
