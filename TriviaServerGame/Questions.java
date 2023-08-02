// Questions class runs through the text file provided, and adds the questions and answers for the trivia into an ArrayList.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Questions {

    private ArrayList<String[]> questions;
    private final int NOT_LAST_ANSWER = 3;
    private final int AMOUNT_QUESTION_ANSWERS = 5;
    private final int QUESTION_LINE = 0;

    public Questions() {
        this.questions = new ArrayList<String[]>();
    }

    public String addFromFile(String fileName) throws FileNotFoundException { // Copying the questions from the file.
        // The text file consists of 40 different questions and their answers.
        Scanner input = new Scanner(new File(fileName));
        int lineCounter = 0; // Counting whether each line from the file represents a question or answer.
        int qCounter = 0;
        String question = "";
        while(input.hasNext()) {
            String str = new String(input.nextLine());
            if (str.isBlank() || str == null) {
                return "Input file not created properly, empty lines are not allowed. Please make sure that a question consists of"
                + " a question line and four answer lines following the question."; // No empty lines in the file.
            }
            if (lineCounter == QUESTION_LINE) { // Reached a new question.
                questions.add(new String[AMOUNT_QUESTION_ANSWERS]);
                (questions.get(qCounter))[lineCounter] = new String(str);
                lineCounter++;
            }
            else if (lineCounter <= NOT_LAST_ANSWER) { // Reaching an answer.
                (questions.get(qCounter))[lineCounter] = new String(str);
                lineCounter++;
            }
            else { // Reaching a final answer and continuing to a new question.
                (questions.get(qCounter))[lineCounter] = new String(str);
                qCounter++;
                lineCounter = 0;
            }
        }
        input.close();
        if (lineCounter != 0 || qCounter == 0) { // An empty final questions database.
            return "Empty input file or insufficient data to complete a trivia question.";
        }
        else {
            return null;
        }
    }

    public ArrayList<String[]> getQuestions() { // Providing the ArrayList with the questions.
        return new ArrayList(this.questions);
    }

}
// End of class Questions.