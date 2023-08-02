// ClientController class represents the user's trivia GUI
// which allows the player to answer questions and gain points using TCP connection with a server that sends the questions.
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Font;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Random;

public class ClientController {

    @FXML
    private CheckBox answer1Check; // GUI variables from SceneBuilder.
    @FXML
    private TextField answer1Text;
    @FXML
    private CheckBox answer2Check;
    @FXML
    private TextField answer2Text;
    @FXML
    private CheckBox answer3Check;
    @FXML
    private TextField answer3Text;
    @FXML
    private CheckBox answer4Check;
    @FXML
    private TextField answer4Text;
    @FXML
    private Button button;
    @FXML
    private TextField questionText;
    @FXML
    private Label pointsLabel;
    @FXML
    private Label timeLabel; // Variables and finals.
    private int pointCounter = 0;
    private int questionCounter = 1;
    private int correct = 0;
    private boolean readyForQuestion = true;
    private String q;
    private String a1;
    private String a2;
    private String a3;
    private String a4;
    private Timer t;
    boolean userGotAnswer;
    boolean buttonClicked;
    private final int DELAY = 1000;
    private final int QUESTION_TIME = 30;
    private final int TIMES_UP = 0;
    private final int ANS_ONE = 1;
    private final int ANS_TWO = 2;
    private final int ANS_THREE = 3;
    private final int ANS_FOUR = 4;
    private final int LOSING_POINTS = 5;
    private final int GAINING_POINTS = 10;
    private final int NUM_QUESTIONS = 20;
    private final int NO_POINTS = 0;
    private final int NUM_OF_ANSWERS = 4;
    private final int FIRST_QUESTION = 1;

    public void initialize() {
        // Initializes the trivia GUI when the controller starts.
        answer1Text.setEditable(false); // Disabling the option to write within the text fields that display the question/answers.
        answer2Text.setEditable(false);
        answer3Text.setEditable(false);
        answer4Text.setEditable(false);
        questionText.setEditable(false);
        pointsLabel.setText("Your points: 0"); // Setting labels for points and timer.
        timeLabel.setText("Answer Timer");
        t = new Timer(DELAY, new ActionListener() { // Creating a new Timer object with an anonymous ActionListener type.

            private int count = QUESTION_TIME; // Timer 30-second countdown for each question.

            @Override // With new question received we activate the timer.
            public void actionPerformed(java.awt.event.ActionEvent e) {
                count--; // Counting from 30 to 0.
                if(count == TIMES_UP) { // When time's up:
                    count = QUESTION_TIME;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            timeLabel.setText("CountDown: " + count);
                            if ((answer1Check.isSelected() == true && correct == ANS_ONE) || (answer2Check.isSelected() == true && correct == ANS_TWO)
                                    || (answer3Check.isSelected() == true && correct == ANS_THREE) || (answer4Check.isSelected() == true && correct == ANS_FOUR)) {
                                userGotAnswer = true;
                            } // Checking whether within the countdown time the user checked the correct answer or not.
                            else if (answer1Check.isSelected() == false && answer2Check.isSelected() == false
                                    && answer3Check.isSelected() == false && answer4Check.isSelected() == false) {
                                // If the user didn't choose any answer, he loses points.
                                JOptionPane.showConfirmDialog(null , "Your 30 seconds are over! No answer, -5 points" , "Time's up!" , JOptionPane.CLOSED_OPTION);
                                pointCounter -= LOSING_POINTS;
                            }
                            if (userGotAnswer == true) {
                                // If the user chose the correct answer he receives 10 points.
                                JOptionPane.showConfirmDialog(null , "Your 30 seconds are over! Your answer is correct, +10 points" , "Time's up!" , JOptionPane.CLOSED_OPTION);
                                pointCounter += GAINING_POINTS;
                            }
                            else {
                                // If the user chose the wrong answer he loses 5 points.
                                JOptionPane.showConfirmDialog(null , "Your 30 seconds are over! Answer is incorrect, -5 points" , "Time's up!" , JOptionPane.CLOSED_OPTION);
                                pointCounter -= LOSING_POINTS;
                            } // Resetting all the counters/text fields/check boxes to default values in order to prepare to the next question.
                            pointsLabel.setText("Your points: " + pointCounter);
                            answer1Check.setSelected(false);
                            answer2Check.setSelected(false);
                            answer3Check.setSelected(false);
                            answer4Check.setSelected(false);
                            questionText.setText("Question");
                            answer1Text.setText("Answer 1");
                            answer2Text.setText("Answer 2");
                            answer3Text.setText("Answer 3");
                            answer4Text.setText("Answer 4");
                            readyForQuestion = true;
                            button.setText("Next Question");
                            questionCounter++;
                        }
                    });
                    t.stop(); // Stopping the timer.
                }
                else if (buttonClicked == true) {
                    // If during the countdown the user has pressed the answering the button, we instantly stop the timer and prepare to receive a new question.
                    // The buttonPressed function handles the pressing of the answer button.
                    buttonClicked = false;
                    readyForQuestion = true;
                    questionCounter++;
                    count = QUESTION_TIME;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            timeLabel.setText("CountDown: " + count); // Resetting the timer counting.
                        }
                    });
                    t.stop();
                }
                else
                // Button was not pressed and the timer is still counting down hence we constantly change the timer on the display so the user knows how long he has left.
                {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            timeLabel.setText("CountDown: " + count);
                        }
                    });
                }
            }

        });
    }

    // The following 4 functions make sure that in case we check the first, second, third or fourth check box - the other ones are unchecked.
    @FXML
    void answer1Pressed(ActionEvent event) { // First checked.
        answer2Check.setSelected(false);
        answer3Check.setSelected(false);
        answer4Check.setSelected(false);
    }

    @FXML
    void answer4Pressed(ActionEvent event) { // Second checked.
        answer1Check.setSelected(false);
        answer2Check.setSelected(false);
        answer3Check.setSelected(false);
    }

    @FXML
    void answer2Pressed(ActionEvent event) { // Third checked.
        answer1Check.setSelected(false);
        answer3Check.setSelected(false);
        answer4Check.setSelected(false);
    }

    @FXML
    void answer3Pressed(ActionEvent event) { // Fourth checked.
        answer1Check.setSelected(false);
        answer2Check.setSelected(false);
        answer4Check.setSelected(false);
    }

    @FXML
    void buttonPressed(ActionEvent event) {
        // Handles the pressing of the next/answering button.
        userGotAnswer = false;
        if (readyForQuestion == true) {
            // First case means that we have requested a new question to be displayed.
            t.start(); // Starting the timer countdown for the question.
            answer1Check.setSelected(false); // Unchecking all the checkboxes.
            answer2Check.setSelected(false);
            answer3Check.setSelected(false);
            answer4Check.setSelected(false);
            new ClientThread(this , "127.0.0.1").start(); // Starting a client thread that requests, receives and sets a new question.
            this.button.setText("Press"); // Changing the button to be an answering button.
            this.readyForQuestion = false; // Switching the button to case 2.
        }
        else {
            // Second case handles the answering of a question displayed on the GUI.
            buttonClicked = true;
            if ((answer1Check.isSelected() == true && this.correct == ANS_ONE) || (answer2Check.isSelected() == true && this.correct == ANS_TWO)
            || (answer3Check.isSelected() == true && this.correct == ANS_THREE) || (answer4Check.isSelected() == true && this.correct == ANS_FOUR)) {
                // Checking whether the answer checked is correct, if it is, we add points.
                this.pointCounter += GAINING_POINTS;
                this.pointsLabel.setText("Your points: " + this.pointCounter);
                JOptionPane.showConfirmDialog(null , "Correct! +10 points" , "" , JOptionPane.CLOSED_OPTION);
            }
            else if (answer1Check.isSelected() == false && answer2Check.isSelected() == false
            && answer3Check.isSelected() == false && answer4Check.isSelected() == false) {
                // Checking whether no answer was checked and if so subtracting points.
                this.pointCounter -= LOSING_POINTS;
                this.pointsLabel.setText("Your points: " + this.pointCounter);
                JOptionPane.showConfirmDialog(null , "No answer! -5 points" , "" , JOptionPane.CLOSED_OPTION);
            }
            else {
                // Else, a wrong answer has been chosen, subtracting points.
                this.pointCounter -= LOSING_POINTS;
                this.pointsLabel.setText("Your points: " + this.pointCounter);
                JOptionPane.showConfirmDialog(null , "Incorrect! -5 points" , "" , JOptionPane.CLOSED_OPTION);
            }
            if (questionCounter == NUM_QUESTIONS + 1) {
                // In this case, the question answered was the twentieth and last one.
                if (pointCounter > NO_POINTS) {
                    // Checking whether the player's final score is good or bad.
                    JOptionPane.showConfirmDialog(null , "Thank you for answering the quiz! Your final score is " + this.pointCounter + " points :)" , "Good Game!" , JOptionPane.CLOSED_OPTION);
                }
                else {
                    JOptionPane.showConfirmDialog(null , "Thank you for answering the quiz! Your final score is " + this.pointCounter + " points :(" , "Better Luck Next Time!" , JOptionPane.CLOSED_OPTION);
                }
                String options[] = {"New Game" , "Exit"}; // Options for the player.
                var selection = JOptionPane.showOptionDialog(null , "What would you like to do" , "" , 0 , 2 , null , options , options[0]);
                if (selection == 0) { // Player chose to play a new game, so we set the GUI to be prepared for a new trivia.
                    pointCounter = NO_POINTS; // Resetting the counters.
                    questionCounter = FIRST_QUESTION;
                    correct = 0;
                    readyForQuestion = true;
                    button.setText("Begin The Trivia!"); // Setting the beginning button.
                    pointsLabel.setText("Your points: " + 0);
                    answer1Check.setSelected(false); // Unchecking check boxes.
                    answer2Check.setSelected(false);
                    answer3Check.setSelected(false);
                    answer4Check.setSelected(false);
                    questionText.setText("Question"); // Resetting texts.
                    answer1Text.setText("Answer 1");
                    answer2Text.setText("Answer 2");
                    answer3Text.setText("Answer 3");
                    answer4Text.setText("Answer 4");
                }
                else {
                    // Else the player chose to end the game and exit the GUI.
                    System.exit(0);
                }
            }
            else {
                // If the question answered wasn't the final, we prepare the button to receive a new question on the next click.
                readyForQuestion = true;
                button.setText("Next Question");
            }
        }
    }

    public void setQuestion(String[] questionGotten) {
        // Set question receives a trivia question with its answers, mixes the answers to show up in random order and sets the question on the screen when requested.
        int n1 = rand(0 , 0 , 0); // Generating random order for the answers in the question.
        int n2 = rand(n1 , 0 , 0);
        int n3 = rand(n1 , n2 , 0);
        int n4 = rand(n1 , n2 , n3);
        q = questionGotten[0]; // Question string.
        a1 = questionGotten[n1]; // Answer strings according to random generated order.
        a2 = questionGotten[n2];
        a3 = questionGotten[n3];
        a4 = questionGotten[n4];
        addToInterface(q , a1 , a2 , a3 , a4);
        if (n1 == 1) { // Checking which answer out of the 4 is the correct one and storing it in a variable.
            correct = ANS_ONE;
        }
        else if (n2 == 1) {
            correct = ANS_TWO;
        }
        else if (n3 == 1) {
            correct = ANS_THREE;
        }
        else {
            correct = ANS_FOUR;
        }
    }

    private void addToInterface(String q , String a1 , String a2 , String a3 , String a4) {
        questionText.setText(questionCounter + ".) " + q); // Setting the question and the answers on the screen and setting the font.
        answer1Text.setText(a1);
        answer2Text.setText(a2);
        answer3Text.setText(a3);
        answer4Text.setText(a4);
        answer1Text.setFont(Font.font(18));
        answer2Text.setFont(Font.font(18));
        answer3Text.setFont(Font.font(18));
        answer4Text.setFont(Font.font(18));
    }

    private int rand(int f1 , int f2 , int f3) {
        // rand returns a random number from 1 to 4 that represents an answer number and is also different from f1, f2, f3 which are ints that have already been chosen.
        int n = 0;
        boolean good = false;
        while (good == false) { // Running while the generated number is same as one of f1, f2 or f3.
            n = new Random().nextInt(NUM_OF_ANSWERS) + 1;
            if (n != f1 && n != f2 && n != f3) {
                good = true;
            }
        }

        return n;
    }

}
// End of class ClientController.