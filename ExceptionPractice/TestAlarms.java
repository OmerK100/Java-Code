import java.util.ArrayList;
import java.util.Scanner;

/* TestAlarms class stores the main function. The class gathers a list of different alarms from the user and displays it. */

public class TestAlarms {

    private ArrayList alarms; /* Attribute that holds the alarms. */
    private final int TRUE = 0;

    public static void main(String[] args) throws BadAlarm { /* Runs the alarm list simulation. */
        TestAlarms test = new TestAlarms(); /* Creating the alarm list. */
        if (test.isEmpty() == false) { /* Checking whether the list is empty. */
            System.out.println("\nYour list:");
            test.process(test.getArrayList()); /* Passing the list to printing. */
        }
        else {
            System.out.println("\nYour list is empty.");
        }
    }

    private TestAlarms() { /* Gathers input from the user in order to create the alarms list. */
        Scanner scan = new Scanner(System.in);
        this.alarms = new ArrayList();
        System.out.println("We'll create a list of different alarms throughout the city."); /* Introduction and instructions for correct input. */
        System.out.println("\nProvide an address and a type. If the type is \"elevator\" provide a level. If the type is \"smoke\" or \"fire\" provide the activator's name.");
        boolean keepGoing = true;
        while (keepGoing == true) { /* Running through a loop that adds input as long as the user wishes to. */
            System.out.println("\nEnter the place's address:"); /* Each alarm type has an address. */
            String address = new String(scan.nextLine());
            System.out.println("\nEnter the alarm type:"); /* Alarm type. Each type uses its own parameters. */
            String type = new String(scan.nextLine());
            if (type.compareTo("elevator") == TRUE) { /* Elevator type. */
                System.out.println("\nEnter the elevator's level:"); /* Elevator's level. */
                String str = new String(scan.nextLine());
                int level = Integer.parseInt(str);
                try { /* Trying to create a new Elevator object with the input and throwing an exception if found. */
                    alarms.add(new Elevator(level , address));
                } catch(BadAlarm ex) {
                    System.out.println();
                    System.out.println(ex);
                    System.out.println("Input inserted improperly");
                }
            }
            else if (type.compareTo("smoke") == TRUE) { /* Smoke type. */
                System.out.println("\nEnter the person's name:"); /* Alarm activator. */
                String name = new String(scan.nextLine());
                try { /* Trying to create a new Smoke object with the input and throwing an exception if found. */
                    alarms.add(new Smoke(name , address));
                } catch(BadAlarm ex) {
                    System.out.println();
                    System.out.println(ex);
                    System.out.println("Input inserted improperly");
                }
            }
            else if (type.compareTo("fire") == TRUE) { /* Fire type. */
                System.out.println("\nEnter the person's name:"); /* Alarm activator. */
                String name = new String(scan.nextLine());
                try { /* Trying to create a new Fire object with the input and throwing an exception if found. */
                    alarms.add(new Fire(name , address));
                } catch(BadAlarm ex) {
                    System.out.println();
                    System.out.println(ex);
                    System.out.println("Input inserted improperly, you can try again.");
                }
            }
            System.out.println("\nWould you like to continue adding more input? Answer \"yes\" or \"no\".");
            /* After adding to the list we check whether the user wants to add another alarm. */
            if ((scan.nextLine()).compareTo("yes") != TRUE) { /* If the user didn't ype yes we stop. */
                keepGoing = false;
                break;
            }
        }
    }

    private void process(ArrayList alarms) { /* process function receives the object's ArrayList, runs through it and prints the different alarms. */
        int smokeCounter = 0;
        for (int i = 0 ; i < alarms.size() ; i++) { /* Loop through the ArrayList. */
            if (alarms.get(i) instanceof Fire) { /* Checking each alarm's type using instanceof to activate the correct action function. */
                System.out.println(((Fire)alarms.get(i)).action()); /* Fire */
            }
            else if (alarms.get(i) instanceof Smoke) {
                System.out.println(((Smoke)alarms.get(i)).action()); /* Smoke */
                smokeCounter++; /* Counting the number of smoke alarms. */
            }
            else if (alarms.get(i) instanceof Elevator) {
                System.out.println(((Elevator)alarms.get(i)).action()); /* Elevator. */
                ((Elevator)alarms.get(i)).reset(); /* Resetting each elevator's level. */
            }
        }
        System.out.println(smokeCounter + " Smoke alarms were detected in the list above."); /* Smokes counted. */
    }

    private ArrayList getArrayList() { /* Getter for the object's ArrayList. */
        return this.alarms;
    }

    private boolean isEmpty() { /* Utility method that checks whether the TestAlarm's object ArrayList is empty or not. */
        if (this.alarms.size() == 0) {
            return true;
        }

        return false;
    }

}
// End of class TestAlarm.