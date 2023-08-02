/* Class BadAlarm represents an exception caused by the Alarm class and its heirs. */

public class BadAlarm extends Exception {

    public BadAlarm(String error) { /* Receives a custom exception string from the different alarms and passes the string to Exception class. */
        super(error);
    }

}
// End of class BadAlarm.