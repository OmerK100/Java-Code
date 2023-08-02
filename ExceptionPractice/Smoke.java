/* Class Smoke represents a smoke alarm. */

public class Smoke extends Alarm {

    protected String Activator; /* Smoke alarm activator, exclusive to this class. There's also the address attribute inherited from Alarm. */

    public Smoke(String activator , String address) throws BadAlarm { /* Smoke builder that receives parameters. */
        super(address); /* Passes the address to the upper class. */
        if (activator == null) { /* If activator parameter is a null/empty/white chars we throw a custom BadAlarm exception. */
            throw new BadAlarm("An attempt to assign null to the activator attribute.");
        }
        else if (activator.compareTo("") == TRUE) {
            throw new BadAlarm("An attempt to create an empty activator attribute.");
        }
        else if ((activator.trim()).length() == TRUE) {
            throw new BadAlarm("An attempt to create an activator attribute with only white chars.");
        }
        this.Activator = new String(activator); /* Else if activator is ok we add it to the object. */
    }

    public String action() { /* The abstract class inherited from Alarm, returns a string that represents the smoke alarm. */
        return ("Smoke alarm details:\n" + super.toString() + "Activator: " + this.Activator + "\n");
        /* String with activator attribute, and toString that was created in Alarm class. */
    }

}
// End of class Smoke.