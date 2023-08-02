/* SudokuController class creates the Sudoku interface that allows the user to create and solve a Sudoku board. */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

public class SudokuController {

    @FXML
    private GridPane grid;
    @FXML
    private TextField addText;
    private final int SIZE = 81; /* Finals. */
    private final int ROW_COL_SIZE = 9;
    private final int CUBE_EDGE = 3;
    private final int TOO_MUCH = 50;
    private final int EMPTY = 0;
    private final int ILLEGAL = -1;
    private final int SPACE_BEFORE_COLOR = 1;
    private boolean setActivated = false; /* Utility variables. */
    private int fillCount = EMPTY; /* Counting how many squares have been filled on the board. */
    private Button[] buttons = new Button[SIZE];
    private int mistakes = 0; /* Mistake counter in the Sudoku. */

    public void initialize() { /* initialize function displays the scene and draws the Sudoku board. */
        addText.setVisible(false); /* Hiding the TextField for entering numbers. */
        for (int i = 0; i < SIZE; i++) { /* Create the 9x9 Sudoku board, each square is a colored button. */
            buttons[i] = new Button("");
            buttons[i].setPrefSize(grid.getPrefWidth() / ROW_COL_SIZE , grid.getPrefHeight() / ROW_COL_SIZE);
            buttons[i].setOnAction(new EventHandler<ActionEvent>() { /* Handling the press of each button. */
                @Override
                public void handle(ActionEvent e) {
                    handleButton(e);
                }
            });
            grid.add(buttons[i] , i % ROW_COL_SIZE , i / ROW_COL_SIZE); /* Adding the buttons to the GridPane on the scene. */
            blackOrWhite(buttons , i); /* Coloring the squares black or white according to their location. */
        }
        /* Dialog box with instructions. */
        JOptionPane.showConfirmDialog(null , "Press 'OK' and set the sudoku board as you want, when ready press 'set' to start." , "Welcome" , JOptionPane.CLOSED_OPTION);
    }

    @FXML
    void clearPressed(ActionEvent event) { /* clearPressed function resets the Sudoku board. */
        addText.setVisible(false);
        for (int i = 0; i < SIZE; i++) { /* Removing each number entered on the board. */
            buttons[i].setText("");
            blackOrWhite(buttons , i);
        }
        setActivated = false; /* Resetting all the counters and flags. */
        fillCount = EMPTY;
        mistakes = 0;
    }

    @FXML
    void setPressed(ActionEvent event) {
        /* setPressed locks down the Sudoku board and starts the game, after pressing all pre-entered numbers are now interchangeable and the player must fill the board. */
        if (setActivated == true) { /* If "set" has already been pressed another press will display a dialog box with a message. */
            JOptionPane.showConfirmDialog(null , "The sudoku board is already set." , "Sudoku already set" , JOptionPane.CLOSED_OPTION);
            for (int i = 0 ; i < SIZE ; i++) {
                /* If a button (square) was pressed and then immediately the player presses "set" the pressing of the square is disabled. */
                if ((textColor(buttons[i].getStyle())).equals("green") == true) {
                    blackOrWhite(buttons , i);
                }
            }
            return;
        }
        setActivated = true;
        addText.setVisible(false);
        for (int i = 0 ; i < SIZE ; i++) {
            /* If a button (square) was pressed and then immediately the player presses "set" the pressing of the square is disabled. */
            if ((textColor(buttons[i].getStyle())).equals("green") == true) {
                blackOrWhite(buttons , i);
            }
        }
        /* Dialog box with a message. */
        JOptionPane.showConfirmDialog(null , "The sudoku board is all set now, enjoy!" , "Sudoku set" , JOptionPane.CLOSED_OPTION);
    }

    @FXML
    void textPressed(ActionEvent event) { /* textPressed handles the insertion of data through the TextField for the Sudoku board. */
        boolean notAdded = false;
        int num = checkLegalText(addText.getText()); /* Checking whether the text is legal (a single digit). */
        if (num == ILLEGAL) { /* If illegal, we show a dialog box and set a flag true to know that nothing will be added to the board. */
            JOptionPane.showConfirmDialog(null , "Incorrect input, please add a digit." , "Error" , JOptionPane.CLOSED_OPTION);
            notAdded = true;
        }
        for (int i = 0 ; i < SIZE ; i++) { /* Running through the squares and looking for pressed buttons (colored green). */
            if ((textColor(buttons[i].getStyle())).equals("green") == true) {
                if (notAdded != true) { /* If the inserted data is legal we put the digit inside the pressed square. */
                    buttons[i].setText("" + num);
                    if (setActivated == false) { /* If "set" wasn't pressed the text in the square becomes red. */
                        buttons[i].setStyle("-fx-text-fill: red;");
                    }
                }
                if (checkLegalSudoku(i) == false && notAdded == false) { /* After the insertion the Sudoku board becomes illegal (according to Sudoku rules). */
                    /* Displaying a dialog box with a warning and counting the number of mistakes. */
                    JOptionPane.showConfirmDialog(null , "Illegal digit placement within the sudoku." , "Error" , JOptionPane.CLOSED_OPTION);
                    mistakes++;
                    if (mistakes >= 5) { /* If the number of mistakes exceeded 5, we display a dialog box and reset the game. */
                        JOptionPane.showConfirmDialog(null , "You failed too much, start again :)" , "Fail" , JOptionPane.CLOSED_OPTION);
                        ActionEvent e = new ActionEvent();
                        clearPressed(e);
                    }
                    buttons[i].setText(""); /* If the insertion was illegal, we delete the inserted number from the board so the game could continue. */
                    notAdded = true; /* Turning on the not-inserted flag. */
                }
                if (setActivated == true || notAdded == true) { /* If nothing was inserted for some reason we turn the pressed button black or white. */
                    blackOrWhite(buttons , i);
                }
                addText.setVisible(false); /* Hiding again the TextField object. */
                if (notAdded != true) { /* If insertion was legal two dialog boxes might appear: */
                    fillCount++; /* Counter for how many squares have been filled up already. */
                    if (setActivated == false && fillCount == TOO_MUCH) { /* Player tries to set too many squares before the game starts. */
                        JOptionPane.showConfirmDialog(null , "Are you trying to fill up the whole board before the game starts?!" , "?!" , JOptionPane.CLOSED_OPTION);
                    }
                    if (fillCount == SIZE) { /* Player has finished the game by filling up the board. */
                        JOptionPane.showConfirmDialog(null , "Well done, you've completed the sudoku!" , "Success" , JOptionPane.CLOSED_OPTION);
                    }
                }
            }
        }
    }

    private void handleButton(ActionEvent event) { /* handleButton handles the pressing of Sudoku square on the board. */
        /* Pressed squares will turn green. */
        boolean flag = true;
        for (int i = 0 ; i < SIZE && flag == true ; i++) {
            if ((textColor(buttons[i].getStyle())).equals("green") == true) { 
                /* If "set" wasn't pressed Sudoku squares are filled with red texts, else square become black or white. */
                if (setActivated == false && buttons[i].getText().equals("") == false) {
                    buttons[i].setStyle("-fx-text-fill: red;");
                    flag = false;
                }
                blackOrWhite(buttons , i);
            }
        }
        Button temp = (Button)event.getSource(); /* Source that was pressed. */
        if (textColor(temp.getStyle()).equals("red") && setActivated == true) { 
            /* An attempt to press a button after "set" that was already filled hides the TextField (interchangeable). */
            addText.setVisible(false);
        }
        else {
            temp.setStyle("-fx-background-color: green;  -fx-border-color: black;");
            /* Else when a square is clicked we make the TextField appear and let the user add the text for the square. */
            addText.setText("Add here:");
            addText.setStyle("-fx-text-fill: #D3D3D3;");
            addText.setVisible(true);
            addText.setOnMouseClicked(e -> { /* Gathering input from the user. */
                addText.setText("");
                addText.setStyle("-fx-text-fill: black;");
            });    
        }
    }

    private int checkLegalText(String text) { 
        /* checkLegalText checks the text that was entered by the user for the Sudoku board and makes sure its legal - only a single digit (white spaces are allowed). */
        int foundDigit = 0;
        int num = 0;
        for (int i = 0 ; i < text.length() ; i++) {
            if (text.charAt(i) != ' ' && text.charAt(i) != '\t' && text.charAt(i) != '\n') {
                if (text.charAt(i) < '0' || text.charAt(i) > '9') { /* Found char that is not a digit and not a white char. */
                    return ILLEGAL;
                }
                else {
                    foundDigit++;
                    num = text.charAt(i) - '0';
                }
            }
        }
        if (foundDigit != 1) {
            return ILLEGAL;
        }

        return num; /* If the text is legal we return the digit that was found as an int. */
    }

    private String textColor(String s) { /* textColor receives a string that represents a color of an object in JAVA and extracts its exact color. */
        String color = new String("");
        int spaceCount = 0;
        for (int i = 0 ; i < s.length() ; i++) {
            if (s.charAt(i) == ' ') { /* Counting spaces until reaching the color. */
                int j = i;
                while (s.charAt(j) != ';') {
                    j++;
                }
                spaceCount++; /* We know exactly how many spaces there are before we reach the color in the text. */
                if (spaceCount == SPACE_BEFORE_COLOR) {
                    color = new String(s.substring(i + 1 , j));
                    return color;
                }
            }
        }

        return color;
    }

    private boolean checkLegalSudoku(int i) { /* CheckLegalSudoku checks whether after the insertion of a number into the board, the board is still legal. */
        int row = i / ROW_COL_SIZE;
        int rowBegin = (row + 1) * ROW_COL_SIZE - ROW_COL_SIZE;
        int column = i % ROW_COL_SIZE;
        int columnBegin = column;
        for (int j = columnBegin ; j < SIZE ; j += ROW_COL_SIZE) {
            /* Scanning the column of the given index and making sure that the same number doesn't appear twice. */
            if (j == i) {
                continue;
            }
            if (buttons[i].getText().equals(buttons[j].getText()) == true) {
                return false;
            }
        }
        for (int j = rowBegin ; j <= rowBegin + ROW_COL_SIZE - 1 ; j++) {
            /* Scanning the row of the given index and making sure that the same number doesn't appear twice. */
            if (j == i) {
                continue;
            }
            if (buttons[i].getText().equals(buttons[j].getText()) == true) {
                return false;
            }
        }
        int rowCube = (int)(Math.floor((double)row / CUBE_EDGE));
        /* Scanning the cube of the given index and making sure that the same number doesn't appear twice. */
        /* The location of the square within one of the nine 3x3 cubes changes the way we scan the cube. */
        int row1 = 0;
        int row2 = 0;
        if (Math.floor((double)(row - 1) / CUBE_EDGE) == rowCube && Math.floor((double)(row - 2) / CUBE_EDGE) == rowCube) {
            /* For the row - case 1 in which the row within the 3x3 cube of the given square is the lowest. */
            row1 = row - 1;
            row2 = row - 2;
        }
        else if (Math.floor((double)(row - 1) / CUBE_EDGE) == rowCube && Math.floor((double)(row + 1) / CUBE_EDGE) == rowCube) {
            /* For the row - case 2 in which the row within the 3x3 cube of the given square is the middle. */
            row1 = row - 1;
            row2 = row + 1;
        }
        else if (Math.floor((double)(row + 1) / CUBE_EDGE) == rowCube && Math.floor((double)(row + 2) / CUBE_EDGE) == rowCube) {
            /* For the row - case 3 in which the row within the 3x3 cube of the given square is the highest. */
            row1 = row + 1;
            row2 = row + 2;
        }
        int columnCube = (int)(Math.floor((double)column / CUBE_EDGE));
        int column1 = 0;
        int column2 = 0;
        if (Math.floor((double)(column - 1) / CUBE_EDGE) == columnCube && Math.floor((double)(column - 2) / CUBE_EDGE) == columnCube) {
            /* For the column - case 1 in which the row within the 3x3 cube of the given square is the rightest. */
            column1 = column - 1;
            column2 = column - 2;
        }
        else if (Math.floor((double)(column - 1) / CUBE_EDGE) == columnCube && Math.floor((double)(column + 1) / CUBE_EDGE) == columnCube) {
            /* For the column - case 2 in which the row within the 3x3 cube of the given square is the middle. */
            column1 = column - 1;
            column2 = column + 1;
        }
        else if (Math.floor((double)(column + 1) / CUBE_EDGE) == columnCube && Math.floor((double)(column + 2) / CUBE_EDGE) == columnCube) {
            /* For the column - case 3 in which the row within the 3x3 cube of the given square is the leftest. */
            column1 = column + 1;
            column2 = column + 2;
        }
        int firstCol = Math.min(Math.min(column1 , column2) , column);
        /* Now according to the information about the all the rows and columns in the 3x3 square that the pressed
        button is part of we can scan the square to make sure that no number appears twice. */
        for (int j = (row + 1) * ROW_COL_SIZE - ROW_COL_SIZE + firstCol ; j <= (row + 1) * ROW_COL_SIZE - ROW_COL_SIZE + 2 + firstCol ; j++) {
            /* Scanning the first column of the 3x3 square. */
            if (i == j) {
                continue;
            }
            if (buttons[i].getText().equals(buttons[j].getText()) == true) {
                return false;
            }
        }
        for (int j = (row1 + 1) * ROW_COL_SIZE - ROW_COL_SIZE + firstCol ; j <= (row1 + 1) * ROW_COL_SIZE - ROW_COL_SIZE + 2 + firstCol ; j++) {
            /* Scanning the second column of the 3x3 square. */
            if (i == j) {
                continue;
            }
            if (buttons[i].getText().equals(buttons[j].getText()) == true) {
                return false;
            }
        }
        for (int j = (row2 + 1) * ROW_COL_SIZE - ROW_COL_SIZE + firstCol ; j <= (row2 + 1) * ROW_COL_SIZE - ROW_COL_SIZE + 2 + firstCol ; j++) {
            /* Scanning the third column of the 3x3 square. */
            if (i == j) {
                continue;
            }
            if (buttons[i].getText().equals(buttons[j].getText()) == true) {
                return false;
            }
        }

        return true; /* No problems were found, the Sudoku board is legal. */
    }

    private void blackOrWhite(Button[] buttons , int i) { /* blackOrWhite runs through the board (Buttons array) and colors the squares black and white. */
        /* 9 squares of 3x3, in first line squares 1 and 3 are black, in line 2 square 2 is black
        and in line 3 squares 1 and 3 are black as well, all the others are white. */
        if (Math.ceil(((double)i + 1) / ROW_COL_SIZE) <= CUBE_EDGE || Math.ceil(((double)i + 1) / ROW_COL_SIZE) >= 2 * CUBE_EDGE + 1) {
            if ((i + 1) % ROW_COL_SIZE <= CUBE_EDGE || (i + 1) % ROW_COL_SIZE >= 2 * CUBE_EDGE + 1) {
                buttons[i].setStyle("-fx-background-color: gray; -fx-border-color: black;");
            }
            else {
                buttons[i].setStyle("-fx-background-color: white; -fx-border-color: black;");
            }
        }
        else {
            if ((i + 1) % ROW_COL_SIZE >= CUBE_EDGE + 1 && (i + 1) % ROW_COL_SIZE <= 2 * CUBE_EDGE) {
                buttons[i].setStyle("-fx-background-color: gray; -fx-border-color: black;");
            }
            else {
                buttons[i].setStyle("-fx-background-color: white; -fx-border-color: black;");
            }
        }
    }
    
}
// End of class SudokuController.