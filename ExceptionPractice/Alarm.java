import java.util.Date;

/* Class Alarm represents a general type of alarm. */

public abstract class Alarm {

    protected Date date; /* Alarm attributes, time and place of occurrence. Protected for inheritance. */
    protected String address;
    protected final int TRUE = 0;
    protected final int DATE_LAST_SPACE = 3; /* Finals that are used to extract the time component from Date objects. */
    protected final int TIME_LENGTH = 9;

    public Alarm(String address) throws BadAlarm { /* Alarm builder that receives an address, checks for errors and creates and object. */
        this.date = new Date(); /* Initializing the time. */
        if (address == null) { /* If address parameter is a null/empty/white chars we throw a custom BadAlarm exception. */
            throw new BadAlarm("An attempt to assign null to the address attribute.");
        }
        else if (address.compareTo("") == TRUE) {
            throw new BadAlarm("An attempt to create an empty address attribute.");
        }
        else if ((address.trim()).length() == TRUE) {
            throw new BadAlarm("An attempt to create an address attribute with only white chars.");
        }
        this.address = new String(address); /* Else if address is ok we add it to the object. */
    }

    public abstract String action(); /* action function is abstract. We'll only initialize it within the specific alarms. */

    public String toString() { /* toString returns a string that represents the general alarm. */
        int spaceCount = 0;
        String time = this.date.toString();
        for (int i = 0 ; i < time.length() ; i++) { /* First we remove all the unnecessary components of the Date object and use only the time part.  */
            if (time.charAt(i) == ' ') {
                spaceCount++; /* Counting spaces in the Date object until reaching the time. */
                if (spaceCount == DATE_LAST_SPACE) {
                    time = time.substring(i , i + TIME_LENGTH);
                }
            }
        }

        return ("Time:" + time + "\n" + "Address: " + this.address + "\n"); /* String that describes the alarm. */
    }

}
// End of class Alarm.