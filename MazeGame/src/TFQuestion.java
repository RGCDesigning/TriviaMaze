
public class TFQuestion implements Question
{

    /**
     * Stores the Question.
     */
    private String myQuestion;
    
    /**
     * Stores the answer to the question as a boolean.
     */
    private boolean myAnswer; 
    
    public TFQuestion()
    {
        
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

}