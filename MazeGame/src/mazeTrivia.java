
import java.util.Scanner;

public class mazeTrivia
{

    public static void main(String[] args)
    {
        
        final String question1 = "Who was the ancient Greek goddess of love and beauty?\n"
                + "(a) Aphrodite\n(b) Calliope\n(c) Athena\n(d) Calypso\n";
        String question2 = "Which U.S. president said, “government of the people, by the people, for the people” in his Gettysburg Address?\n"
                + "(a) Abraham Lincoln\n(b) George Washington\n(c) Thomas Jefferson\n(d) James Buchanan\n";
        String question3 = "In which U.S. city is NASA’s Mission Control Center located?\n"
                + "(a) Huntsville, Alabama\n(b) Houston, Texas\n(c) Hampton, Virginia\n(d) Cape Canaveral, Florida\n";
        String question4 = "In meteorology, what name is given to a line of equal pressure on a map?\n"
                + "(a) Isotherm\n(b) Isobar\n(c) Isochor\n(d) Isoquant\n";
        String question5 = "What does the Q in IQ stand for?\n"
                + "(a) Quantity\n(b) Quorum\n(c) Quality\n(d) Quotient\n";

        test[] questions = { new test(question1, "a"), new test(question2, "a"), new test(question3, "b"),
                new test(question4, "b"), new test(question5, "d") };

        takeTest(questions);
    }

    public static void takeTest(test[] questions)
    {
        int score = 0;
        Scanner scan = new Scanner(System.in);

        for (int i = 0; i < questions.length; i++)
        {
            System.out.println(questions[i].prompt);
            String answer = scan.nextLine();

            if (answer.equals(questions[i].answer))
            {
                score++;

            }

        }
        System.out.println("You got " + score);

    }

}
