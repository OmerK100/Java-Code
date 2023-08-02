/* RemindersController class creates the interface for the reminders application. */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;

public class RemindersController {

    @FXML
    private ComboBox<String> dayCombo; /* Attributes. */
    @FXML
    private ComboBox<String> monthCombo;
    @FXML
    private ComboBox<String> yearCombo;
    @FXML
    private TextField reminderText;
    private Hashtable<Date , String> reminders = new Hashtable(); /* Data structure to store the reminders. */
    private final int MIN_DAY = 1; /* Finals. */
    private final int MAX_DAY_FIRST = 31;
    private final int MAX_DAY_SECOND = 30;
    private final int MAX_DAY_THIRD = 28;
    private final int MIN_MONTH = 1;
    private final int MAX_MONTH = 12;
    private final int MIN_YEAR = 2023;
    private final int MAX_YEAR = 2100;
    private final int BEGINNING = 0;
    private final int COUNTER_EMPTY = 0;
    private final char MAX_DIGIT = '9';
    private final char MIN_DIGIT = '0';
    private final int OK = 0;
    private final int ADD = 0;
    private final int EMPTY = 0;
    private final int MAXIMAL_DAY_LENGTH = 2;
    private final int YEAR_LENGTH = 4;

    public void initialize() { /* initialize starts the app and calls to set the combo-boxes. */
        initComboBoxes();
    }

    private void initComboBoxes() { /* initComboBoxes initiates the combo-boxes with possible month and year numbers. */
        for (int i = MIN_MONTH ; i <= MAX_MONTH ; i++) {
            monthCombo.getItems().add(i + "");
        }
        for (int i = MIN_YEAR ; i <= MAX_YEAR ; i++) {
            yearCombo.getItems().add(i + "");
        }
    }

    @FXML
    void monthChosen(ActionEvent event) { /* monthChosen initiates the day combo-box with its possible values according to the chosen month: 31, 30 or 28 days.*/
        dayCombo.getItems().clear(); /* Clearing the last values attached to the day combo box in order to add new values. */
        if (monthCombo.getValue().equals("1") || monthCombo.getValue().equals("3") || monthCombo.getValue().equals("5") || monthCombo.getValue().equals("7")
        || monthCombo.getValue().equals("8") || monthCombo.getValue().equals("10") || monthCombo.getValue().equals("12")) { /* 31-day months. */
            for (int i = MIN_DAY ; i <= MAX_DAY_FIRST ; i++) {
                dayCombo.getItems().add(i + "");
            }
        }
        else if (monthCombo.getValue().equals("4") || monthCombo.getValue().equals("6") || monthCombo.getValue().equals("9") || monthCombo.getValue().equals("11")) {
            for (int i = MIN_DAY ; i <= MAX_DAY_SECOND ; i++) { /* 30-day months. */
                dayCombo.getItems().add(i + "");
            }
        }
        else if (monthCombo.getValue().equals("2")) {
            for (int i = MIN_DAY ; i <= MAX_DAY_THIRD ; i++) { /* February, 28 days. */
                dayCombo.getItems().add(i + "");
            }
        }
        /* Else nothing will show. */
    }

    @FXML
    void savePressed(ActionEvent event) {
        /* savePressed handles the pressing of the "Save Reminder" button which stores the reminder from the text-field in the hashtable. */
        String reminder = reminderText.getText(); /* Reminder to store in the hashtable. */
        String dayStr = dayCombo.getSelectionModel().getSelectedItem(); /* Date (key) data for the reminder. */
        String monthStr = monthCombo.getSelectionModel().getSelectedItem();
        String yearStr = yearCombo.getSelectionModel().getSelectedItem();
        if (dayStr != null && monthStr != null && yearStr != null) { /* Making sure a date was chosen using the combo-boxes. */
            int day = Integer.parseInt(dayStr);
            int month = Integer.parseInt(monthStr);
            int year = Integer.parseInt(yearStr);
            Date date = new Date(day , month , year);
            if (reminder == null || reminder == "") { /* No reminder in the text-field, showing a message.*/
                JOptionPane.showConfirmDialog(null , "No reminder to save." , "Note" , JOptionPane.CLOSED_OPTION);
            }
            else {
                reminders.put(date , reminder); /* Adding the reminder to the hashtable with the date being its key. */
            }
        }
        else { /* No date, showing a message. */
            JOptionPane.showConfirmDialog(null , "Please make sure to fill all the date combo boxes." , "Note" , JOptionPane.CLOSED_OPTION);
        }
    }

    @FXML
    void showPressed(ActionEvent event) {
        /* showPressed handles the pressing of the "Show" button that when pressed, reads the date and displays the date's hash value reminder in the text-field. */
        String dayStr = dayCombo.getSelectionModel().getSelectedItem(); /* Gathering the date data. */
        String monthStr = monthCombo.getSelectionModel().getSelectedItem();
        String yearStr = yearCombo.getSelectionModel().getSelectedItem();
        if (dayStr != null && monthStr != null && yearStr != null) { /* Making sure there's a date in the combo-boxes. */
            int day = Integer.parseInt(dayStr);
            int month = Integer.parseInt(monthStr);
            int year = Integer.parseInt(yearStr);
            Date date = new Date(day , month , year); /* Date key. */
            if (reminders.get(date) == null || reminders.get(date) == "") {
                reminderText.setText(" "); /* If there's no reminder for the date key we display an empty text-field. */
            }
            else{
                reminderText.setText(reminders.get(date)); /* Else we display the reminder in the text-field. */
            }
        }
        else { /* No date in the combo-boxes. */
            JOptionPane.showConfirmDialog(null , "Please make sure to fill all the date combo boxes." , "Note" , JOptionPane.CLOSED_OPTION);
        }
    }

    @FXML
    void finishPressed(ActionEvent event) {
        /* finishPressed handles the pressing of the finish button that allows us to write all the reminders to a file. */
        String options[] = {"OK" , "Cancel"}; /* Options to continue or cancel. */
        var selection = JOptionPane.showOptionDialog(null , "Please provide a file name to save the reminders into." , "Saving to a file" , 0 , 2 , null , options , options[0]);
        if (selection == OK) {
            String file = "";
            String name = JOptionPane.showInputDialog(null , file); /* Gathering a file name to save the data into. */
            writeToFile(name); /* Passing the file name to writing. */
        }
    }

    @FXML
    void addPressed(ActionEvent event) {
        /* addPressed handles the pressing of the "Add File" which allows us to add reminders to the app from a file. */
        String options[] = {"Add a file" , "Cancel"}; /* Options to continue or cancel. */
        var selection = JOptionPane.showOptionDialog(null , "Would you like to add reminders from a text file?" , "Add?" , 0 , 2 , null , options , options[0]);
        if (selection == ADD) {
            String file = "";
            JOptionPane.showConfirmDialog(null , "Type the name of the file." , "Note" , JOptionPane.CLOSED_OPTION);
            String input = JOptionPane.showInputDialog(null , file); /* Gathering a file name to extract the data from. */
            readFromFile(input); /* Passing the file name to reading. */
        }
    }

    @FXML
    void resetPressed(ActionEvent event) {
        /* resetPressed handles the pressing of the reset button that clears all the reminders saved and clears the text-field. */
        reminders.clear();
        reminderText.setText("");
    }

    private Date okDate(String date) {
        /* Utility function that reads a string from a file and turns it into a Date object. Should be written as: day.month.year */
        String dayStr = null; /* strings for each component of the Date object. */
        String monthStr = null;
        String yearStr = null;
        int dotCounter = COUNTER_EMPTY;
        boolean foundDot = false;
        for (int i = BEGINNING ; i < date.length() && foundDot == false ; i++) {
            if (date.charAt(i) == '.') { /* After finding the first dot, before it is supposed to be the day. */
                foundDot = true; /* First dot found. */
                dayStr = date.substring(BEGINNING , i);
                boolean foundDot2 = false;
                for (int j = i + 1 ; j < date.length() && foundDot2 == false ; j++) {
                    if (date.charAt(j) == '.') { /* Reaching the second dot, before it should the month and after it the year. */
                        foundDot2 = true; /* Second dot found. */
                        monthStr = date.substring(i + 1 , j);
                        yearStr = date.substring(j + 1 , date.length());
                        return (createDate(dayStr , monthStr , yearStr)); /* Checking whether the components found are legit and if so returning a Date object. */
                    }
                }
            }
        }

        return null;
    }

    private Date createDate(String dayStr , String monthStr , String yearStr) {
        /* Utility function that receives three strings and checks whether they represent a day, a month and a year and if so returns a Date object. */
        for (int z = BEGINNING ; z < dayStr.length() ; z++) { /* Checking that day is a number and is between 1 and 31. */
            if (dayStr.charAt(z) < MIN_DIGIT || dayStr.charAt(z) > MAX_DIGIT || dayStr.length() == EMPTY || dayStr.length() > MAXIMAL_DAY_LENGTH) {
                return null;
            }
        }
        int day = Integer.parseInt(dayStr); /* Creating an int for the day. */
        for (int z = BEGINNING ; z < monthStr.length() ; z++) { /* Checking that month is a number and is between 1 and 12. */
            if (monthStr.charAt(z) < MIN_DIGIT || monthStr.charAt(z) > MAX_DIGIT || monthStr.length() == 0 || monthStr.length() > 2) {
                return null;
            }
        }
        int month = Integer.parseInt(monthStr); /* Creating an int for the month. */
        for (int z = BEGINNING ; z < yearStr.length() ; z++) { /* Checking that year is a number and is between 2023 and 2100. */
            if (yearStr.charAt(z) < MIN_DIGIT || yearStr.charAt(z) > MAX_DIGIT || yearStr.length() != YEAR_LENGTH) {
                return null;
            }
        }
        int year = Integer.parseInt(yearStr); /* Creating an int for the year. */
        if (year < MIN_YEAR || year > MAX_YEAR || month < MIN_MONTH || month > MAX_MONTH || day < MIN_DAY || day > MAX_DAY_FIRST) {
            return null;
        }
        Date d = new Date(day , month , year); /* Creating and returning a Date object, if something was wrong we return a null. */
        return d;
    }

    private void writeToFile(String name) { /* Utility function that writes the reminders hashtable data to a file. */
        try {
            FileWriter fw = new FileWriter(name + ".txt"); /* Creating a new file with the name received. */
            fw.write("Reminders"); /* Title. */
            fw.write("\n");
            fw.write("\n");
            Iterator<Date> iterator = reminders.keySet().iterator(); /* Iterator for the hashtable. */
            while (iterator.hasNext()) {
                Date date = iterator.next(); /* Running through the reminders hashtable, creating a Date object from each key and writing it. */
                fw.write(date.toString());
                fw.write(": ");
                fw.write(reminders.get(date)); /* Extracting and writing the reminder that belongs to the Date key. */
                fw.write("\n");
                fw.write("\n");
            }
            fw.close();
        }
        catch(IOException e) { /* An exception might occur while creating the file, if there is we notify. */
            JOptionPane.showConfirmDialog(null , "Reminders output file creation failure." , "Failure" , JOptionPane.CLOSED_OPTION);
        }
    }

    private void readFromFile(String input) { /* Utility function that reads reminders from a file and puts them in the hashtable. */
        try {
            Scanner scan = new Scanner(new File(input + ".txt"));
            while (scan.hasNext()) { /* Scanning the file. */
                String str = new String(scan.nextLine());
                boolean foundSemicolon = false;
                for (int i = BEGINNING ; i < str.length() && foundSemicolon == false ; i++) {
                    /* Correct format for a string in the file is: Date: reminder */
                    if (str.charAt(i) == ':') { /* Before the semicolon is the date (key) and after it is the reminder (value). */
                        foundSemicolon = true;
                        String str1 = str.substring(BEGINNING , i);
                        String str2 = str.substring(i + 2 , str.length());
                        Date date = okDate(str1); /* Checking that the read date is okay and creating a Date object from the string. */
                        if (date != null) {
                            reminders.put(date , str2); /* Matching the reminder to the Date object. */
                        }
                    }
                }
            }
            scan.close();
        } catch (FileNotFoundException e) { /* An exception might occur while trying to read a file, if there is we notify. */
            JOptionPane.showConfirmDialog(null , "File opening failure." , "Note" , JOptionPane.CLOSED_OPTION);
        }
    }

}
// End of class RemindersController.