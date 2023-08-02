/* Class Fire represents a fire alarm, an advanced type of smoke alarm. */

public class Fire extends Smoke {

    protected boolean active;
    /* Fire alarm active or not, exclusive to this class. There's also the other attributes inherited from Smoke that inherits from Alarm. */

    public Fire(String activator , String address) throws BadAlarm { /* Fire builder that receives parameters. */
        super(activator , address); /* Passes the parameters to the upper class. */
        this.active = true; /* Fire alarm - on. */
    }

    public String action() { /* Overrides action from the Alarm class, returns a string that represents the fire alarm. */
        this.active = false; /* Fire alarm - off. */
        return ("Fire alarm details:\n" + super.toString() + "Activator: " + this.Activator + "\n");
        /* String with the inherited activator attribute, and toString that was created in Alarm class. */
    }

}
// End of Fire class.