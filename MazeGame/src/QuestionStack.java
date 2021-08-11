/**
 * Question Stack.
 * @author Mylo
 * @version 7.18.21
 */

public class QuestionStack 
{
    /**
     * The maximum size of the stack.
     */
    private int myMaxSize;
    
    /**
     * The array that stores the question stack. LIFO.
     */
    private Question[] myQuestionStack;
    
    /**
     * The top of the stack.
     */
    private int myStackTop;
    
    public QuestionStack(final int theMaxSize)
    {
        myMaxSize = theMaxSize;
        myStackTop = -1;
        myQuestionStack = new Question[myMaxSize];
    }
    
    /**
     * Pushes a new Question on to the stack.
     * @param theQuestion
     */
    public void push(final Question theQuestion)
    {
        myStackTop++;
        myQuestionStack[myStackTop] = theQuestion;
    }
    
    /**
     * Pops an item off the top of the stack.
     * @return Returns the item popped off.
     */
    public Question pop()
    {
        return myQuestionStack[myStackTop--];
    }
    
    /**
     * Looks at the top item of the stack without popping.
     * @return Return the top item of the stack.
     */
    public Question peek()
    {
        return myQuestionStack[myStackTop];
    }
    
    /**
     * Checks if the stack is empty.
     * @return Returns the status of the stack.
     */
    public boolean isEmpty()
    {
        if (myStackTop == -1)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if the stack is full.
     * @return Returns the status of the stack.
     */
    public boolean isFull()
    {
        if (myStackTop == myMaxSize - 1)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Gets the size of the stack.
     * @return Returns the size of the stack.
     */
    public int size()
    {
        return myStackTop + 1;
    }
    
}
