import java.util.Scanner;

/* Main class stores the main function. The class executes the various arithmetic operations we wrote on two BigInts gathered from the user. */

public class Main {

    public static void main(String[] args) { /* main function demonstrates the usage of the arithmetic functions. */
        System.out.println("Enter two strings that represent integers, please use signs of '+' or '-'"); /* Introduction and guidance. */
        Scanner scan = new Scanner(System.in);
        boolean keepGoing = true;
        while (keepGoing == true) { /* Running and gathering input from the user as long as he wishes to. */
            System.out.println("First number:");
            String first = new String(scan.nextLine());
            System.out.println("Second number:");
            String second = new String(scan.nextLine());
            try {
                BigInt a = new BigInt(first); /* Creating BigInt objects according to the user's input. */
                BigInt b = new BigInt(first);
                BigInt c = new BigInt(first);
                BigInt d = new BigInt(first);
                BigInt e = new BigInt(first);
                BigInt f = new BigInt(second);
                BigInt g = new BigInt(second);
                BigInt h = new BigInt(second);
                BigInt i = new BigInt(second);
                BigInt j = new BigInt(second);
                System.out.println("\nYour BigInts:"); /* Displaying the BigInts after "fixing them" (removing unnecessary 0's and ect). */
                System.out.print("first: ");
                System.out.print(a.toString());
                System.out.print("   ,   ");
                System.out.print("second: ");
                System.out.print(f.toString());
                System.out.println("\n\nComparison, summation, subtraction, multiplication and division of the strings:\n");
                if (a.compareTo(f) == 0) { /* Executing and printing the outcomes of different operations. */
                    System.out.println("Comparison: " + a.toString() + " is equal to " + f.toString() + "\n");
                }
                else if (a.compareTo(f) == 1) {
                    System.out.println("Comparison: " + a.toString() + " is larger than " + f.toString() + "\n");
                }
                else if (a.compareTo(f) == -1) {
                    System.out.println("Comparison: " + a.toString() + " is smaller than " + f.toString() + "\n");
                }
                System.out.println("Sum: " + (b.plus(g)).toString() + "\n");
                System.out.println("Subtraction: " + (c.minus(h)).toString() + "\n");
                System.out.println("Product: " + (d.multiply(i)).toString() + "\n");
                try {
                    System.out.println("Division: " + (e.divide(j)).toString() + "\n");
                } catch(ArithmeticException ex1) {
                    /* If the second BigInt gathered from the user represents 0 we throw an exception because there will be an attempt to divide by 0. */
                    System.out.println(ex1);
                }
                System.out.println("\nContinue to the next calculation? Answer \"yes\" or \"no\"."); /* Asking the user whether he wants to test the functions again. */
                if ((scan.nextLine()).compareTo("yes") != 0) { /* if the answer is not "yes" we stop. */
                    keepGoing = false;
                    System.out.println("-----------------main ending-----------------");
                    break;
                }
                System.out.println("\nEnter two strings again");
            } catch(IllegalArgumentException ex2) { /* While gathering the data, we make sure the user entered the data properly, else we throw an exception. */
                System.out.println(ex2);
                System.out.println("\nPlease type in the ints correctly, a '+' or '-' in the beginning and digits from 0 to 9");
            }
        }
    }

}
// End of class Main.