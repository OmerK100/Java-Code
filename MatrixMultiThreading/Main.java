/* Matrix product calculator with input output and multithreading - Omer Komissarchik - 215314725 */
/* Main class runs the matrix multi-threading simulation through user input. */
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("A demonstration of multi-threading through matrix multiplication.\n");
        System.out.println("Enter the first matrix's number of rows:"); /* Scanning the first matrix's number of rows. */
        Scanner scan = new Scanner(System.in);
        while (!scan.hasNextInt()) {
            System.out.println("Invalid input, please try again:"); /* If there's an error, we repeat the request. */
            scan.next();
        }
        int n = scan.nextInt();
        System.out.println("Enter the first matrix's number of columns (which is also the second matrix's number of rows):");
        /* Scanning the first matrix's number of columns. */
        while (!scan.hasNextInt()) {
            System.out.println("Invalid input, please try again:"); /* If there's an error, we repeat the request. */
            scan.next();
        }
        int m = scan.nextInt();
        System.out.println("Enter the second matrix's number of columns:"); /* Scanning the second matrix's number of columns. */
        while (!scan.hasNextInt()) {
            System.out.println("Invalid input, please try again:"); /* If there's an error, we repeat the request. */
            scan.next();
        }
        int p = scan.nextInt();
        if (!(n < 0 || p < 0 || m <0)) { /* Passing the lengths as parameters for the creation of the monitor and the threads. */
            runThreads(n , m , p);
        }
        else {
            System.out.println("Negative input is not allowed.");
        }
    }

    private static void runThreads(int n , int m , int p) {
        OrderMonitor monitor = new OrderMonitor(n , m , p); /* Creating a monitor that will manage the access of the threads into the matrix objects. */
        MatrixThread threads[] = new MatrixThread[n * p];
        for (int i = 0 ; i <= n * p - 1 ; i++)  { /* Creating the threads, same number as the matrix's product size. */
            threads[i] = new MatrixThread(i + 1 , monitor);
        }
        for (int i = n * p - 1 ; i >= 0 ; i--)  { /* Running the threads. */
            threads[i].start();
        }
    }

}
// End of class Main.
