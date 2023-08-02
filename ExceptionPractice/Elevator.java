/* Class Elevator represents an alarm within an elevator. */

public class Elevator extends Alarm {

    protected int floor; /* Elevator's level, exclusive to this class. There's also the address attribute inherited from Alarm. */
    private final int BASE_LEVEL = 0; /* Used to reset the elevator's floor. */

    public Elevator(int floor , String address) throws BadAlarm { /* Elevator builder that receives parameters. */
        super(address); /* Passes the address to the upper class. */
        this.floor = floor;
    }

    public String action() { /* The abstract class inherited from Alarm, returns a string that represents the elevator alarm. */
        return ("Elevator alarm details:\n" + super.toString() + "Floor: " + this.floor + "\n");
        /* String with floor attribute, and toString that was created in Alarm class. */
    }

    public void reset() { /* reset function resets the elevator's level to base - 0. */
        this.floor = BASE_LEVEL;
    }

}
// End of class Elevator.