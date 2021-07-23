/**
 * Question interface.
 * @author Mylo
 * @version 7.18.21
 */

public interface Question 
{
    
    /**
     * Gets the question.
     * @return Returns the question's text.
     */
    String getQuestion();
    
    /**
     * Gets the answer.
     * @return Returns the question's answer.
     */
    String getAnswer();
    
    /**
     * Prints a readable version of readable version of the question and answer object.
     * @return The readable version of the Q and A.
     */
    @Override
    String toString();
    
}
