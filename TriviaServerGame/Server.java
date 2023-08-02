// Server class creates the server that sends trivia questions to the client per request.
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server {

    private Questions trivia;
    private final int QUESTIONS_TOTAL = 20;
    private final int EMPTY = 0;
    private static final int AMOUNT_QUESTION_ANSWERS = 5;
    private int questionCounter = QUESTIONS_TOTAL;
    private boolean flag = true;
    private final int PORT = 3333;

    public Server() {
        // Initializing the server by reading the questions and their answers from a text file and storing them in an array.
        this.trivia = new Questions(); // Questions is the collection object that stores the questions.
        // We'll have to use 20 random questions out of the 40 we have.
        try {
            this.trivia.addFromFile("trivia.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ServerSocket sc = null;
        ArrayList<String[]> questions = trivia.getQuestions();
        try {
            sc = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        receiveAndSend(questions , sc); // Passing the questions and socket to receiving and sending packets.
    }

    private void receiveAndSend(ArrayList<String[]> questions , ServerSocket sc) {
        // The socket receives a request for a question, pulls and removes a random question from the ArrayList and sends it back to the client.
        Socket s = null;
        while(flag) {
            if (this.questionCounter == EMPTY) {
                this.questionCounter = QUESTIONS_TOTAL;
                questions = trivia.getQuestions();
            }
            try {
                s = sc.accept();
            } catch (IOException e) {
                flag = false;
                throw new RuntimeException(e);
            }
            String[] question = new String[AMOUNT_QUESTION_ANSWERS];
            int index = getRandomQuestion(questions , question);
            questions.remove(index);
            new ServerThread(s , question).start(); // Creating a server thread with the chosen question the sends the question to the client.
            this.questionCounter--;
        }
        try {
            sc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getRandomQuestion(ArrayList<String[]> questions , String[] question) {
        // Chooses a random index that represents a possible question in the ArrayList, creates a question with answers with the chosen index and returns the index.
        int index = new Random().nextInt(questions.size());
        for (int i = 0 ; i < AMOUNT_QUESTION_ANSWERS ; i++) {
            question[i] = (questions.get(index))[i];
        }

        return index;
    }

    public static void main(String[] args) {
        // Creating and running a new instance of the server.
        new Server();
    }

}
// End of class Server.