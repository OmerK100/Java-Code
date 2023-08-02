/* Advanced Java - MAMAN14EX1 - Omer Komissarchik - 215314725 */
/* Main class executes a demonstration of the SortedGroup class and the functions that it has through with Student objects that implement Comparable. */
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    private static final int PASSING_GRADE = 60; /* Finals. */
    private static final int BEGINNING = 0;
    private static final char MIN_DIGIT_ZERO = '0';
    private static final char MAX_DIGIT_NINE = '9';
    private static final int EMPTY = 0;
    private static final int EQUAL = 0;
    private static final int MIN_GRADE = 0;
    private static final int MAX_GRADE = 100;
    private static final int TOO_SMALL_SIZE = 1;
    private static final int CIRCLE_OF_THREE = 3;
    private static final int DIVIDABLE_BY_THREE = 0;
    private static final int NOT_EMPTY = 1;

    public static void main(String[] args) /* main function creates the variables executes the demonstration. */
    {
        SortedGroup<Student> collection = new SortedGroup<Student>(); /* New SortedGroup object for students. */
        int collectionSize = userInsert(collection);
        /* Letting the user insert data for as many students as he wants and demonstrating the ability to add students to the group by order of grades. */
        printGroup(collection , collectionSize); /* Printing the group created by the user (by their order of grades). */
        Scanner scan = new Scanner(System.in);
        System.out.println("\nType \"ok\" to see to removal of some students from the list by the application's chosen way."); /* User confirmation. */
        if (confirm(scan.nextLine()) == false) {
            return;
        }
        collectionSize = removal(collection , collectionSize); /* Demonstrating the removal of several students from the group. */
        if (printGroup(collection , collectionSize) == false) { /* Printing the group after the removal. */
            return;
        }
        System.out.println("\nType \"ok\" to see to reduction of the list and preservation of the students whose grades are higher than 60."); /* User confirmation. */
        if (confirm(scan.nextLine()) == false) {
            return;
        }
        UseSortedGroup students = new UseSortedGroup();
        Student dummy = new Student("" , "" , PASSING_GRADE);
        SortedGroup<Student> collection2 = students.reduce(collection , dummy);
        /* Demonstrating the reduction of students with grades lower or equal to 60 from the group. */
        Iterator<Student> iterator = collection2.iterator();
        if (iterator.hasNext() == true) { /* Printing the new group after the reduction (making sure first that the group isn't empty). */
            printGroup(collection2 , NOT_EMPTY);
        }
        else {
            System.out.println("\nNothing to print, the list is empty.");
        }
    }

    private static int userInsert(SortedGroup collection) {
        /* userInsert gathers data from the user that is used to create Student object and stores them in the SortedGroup parameter. */
        Scanner scan = new Scanner(System.in);
        int size = EMPTY;
        System.out.println("We'll create a group of students."); /* Introduction and instructions for correct input. */
        System.out.println("\nProvide a name, an ID and a grade.");
        boolean keepGoing = true;
        while (keepGoing == true) { /* Running through a loop that adds input as long as the user wishes to. */
            Student next = addStudent(); /* Creating the Student object by gathering the data using addStudent. */
            if (next != null) { /* Making sure the Student object was created correctly and adding it to the group. */
                collection.add(next);
                size++;
                System.out.println("\nWould you like to continue adding more input? Answer \"yes\" or \"no\".");
            }
            else{
                System.out.println("\nSome data was inserted improperly, try again please.");
                continue;
            }
            scan.skip("\\R?");
            if (scan.nextLine().compareTo("yes") != EQUAL) { /* If the user didn't type yes we stop. */
                keepGoing = false;
                return size;
            }
        }

        return size; /* Returning the size of the group that was created. */
    }

    private static Student addStudent() { /* addStudent scans data from the user and creates the Student object. */
        Scanner scan = new Scanner(System.in); /* Gathering data. */
        System.out.println("\nEnter the student's name:"); /* Student's name. */
        String name = new String(scan.nextLine());
        System.out.println("\nEnter the student's ID:"); /* Student's ID. */
        String ID = new String(scan.nextLine());
        System.out.println("\nEnter the student's grade:"); /* Student's grade. */
        String gradeStr = scan.nextLine();
        if (onlySpace(name) == false && onlySpace(ID) == false && isNum(ID) == true && onlySpace(gradeStr) == false && isNum(gradeStr) == true) {
            /* Making sure the data is proper, creating the Student object and returning it. */
            int grade = Integer.parseInt(gradeStr);
            if (grade >= MIN_GRADE && grade <= MAX_GRADE) {
                Student next = new Student(name, ID, grade);
                return next;
            }
            return null;
        }

        return null; /* Else, returning a null. */
    }

    private static boolean onlySpace(String name) { /* onlySpace checks whether a string is made only of white chars or not. */
        if (name == null) {
            return true;
        }
        for (int i = BEGINNING ; i < name.length() ; i++) { /* Running through the string and checking the chars. */
            if (name.charAt(i) != ' ' && name.charAt(i) != '\t' && name.charAt(i) != '\n') { /* Checking for non-white char. */
                return false;
            }
        }

        return true;
    }

    private static boolean isNum(String x) { /* Utility method that checks whether a string represents an integer. */
        for (int i = BEGINNING ; i < x.length() ; i++) { /* Running through the string and checking whether all the chars are digits. */
            if (x.charAt(i) < MIN_DIGIT_ZERO || x.charAt(i) > MAX_DIGIT_NINE) {
                return false;
            }
        }

        return true;
    }

    private static boolean printGroup(SortedGroup collection , int collectionSize) {
        /* printGroup uses an iterator to run through the group and print all the Student's in the group with their data. */
        System.out.println("\n");
        if (collectionSize != EMPTY) { /* Non-empty group. */
            Iterator<Student> iterator = collection.iterator();
            while(iterator.hasNext() == true) { /* Iterating and printing the students. */
                Student next = iterator.next();
                System.out.println(next.toString());
            }
            return true;
        }
        else { /* Empty group. */
            System.out.println("\nNothing to print, the list is empty.");
            return false;
        }
    }

    private static boolean confirm(String str) { /* confirm makes sure that the user typed "ok" for confirmation when asked to. */
        if (str.compareTo("ok") != EQUAL) {
            return false;
        }

        return true;
    }

    public static int removal(SortedGroup collection , int collectionSize) {
        /* removal demonstrates removal of students from the group in the following way:
        if the size of the group is one or two we remove the first student, else we remove students in the group that their position is dividable by three. */
        if (collectionSize == TOO_SMALL_SIZE || collectionSize == TOO_SMALL_SIZE + 1) { /* Size of one or two, removing the first student using the iterator. */
            Iterator<Student> iterator = collection.iterator();
            Student next = iterator.next();
            collection.remove(next);
            collectionSize--;
        }
        else {
            SortedGroup<Student> collectionToDelete = new SortedGroup<Student>();
            /* Size of three or larger, iterating through the group and removing the correct students. */
            int counter = 1;
            Iterator<Student> iterator = collection.iterator();
            while(iterator.hasNext() == true) {
                Student next = iterator.next();
                if (counter % CIRCLE_OF_THREE == DIVIDABLE_BY_THREE) { /* Using the counter we check whether the position of the next student is dividable by three. */
                    collectionToDelete.add(next); /* Adding the students we want to remove to a new group. */
                }
                counter++;
            }
            Iterator<Student> iterator2 = collectionToDelete.iterator();
            while(iterator2.hasNext() == true) { /* Iterating through the new group and removing each one of its students from the original parameter group. */
                Student next = iterator2.next();
                collection.remove(next);
                collectionSize--;
            }
        }

        return collectionSize;
    }

}
// End of class Main.