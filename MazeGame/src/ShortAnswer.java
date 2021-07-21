
public class ShortAnswer implements Question
{
    /**
     * 
     */
    private String myQuestion;
    
    /**
     * 
     */
    private String myAnswer;
    
    public ShortAnswer()
    {
        
    }
    
    /**
     * Verifies a correct answer.
     * @param theInput The answer to verify.
     * @return Returns whether or not the correct answer was input.
     */
    boolean verifyAnswer(final String theInput)
    {
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
    
}
