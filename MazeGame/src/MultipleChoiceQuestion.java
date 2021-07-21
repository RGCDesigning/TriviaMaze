
public class MultipleChoiceQuestion implements Question
{
    
    /**
     * Stores the choices to the question.
     */
    private String[] myChoices;
    
    /**
     * Stores the Question.
     */
    private String myQuestion;
    
    /**
     * Stores the answer to the question as an integer.
     */
    private int myAnswer; 
    
    public MultipleChoiceQuestion()
    {
        
    }
    
    /**
     * Verifies a correct answer.
     * @param theInput The answer to verify.
     * @return Returns whether or not the correct answer was input.
     */
    public boolean verifyAnswer(final int theInput)
    {
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

}
