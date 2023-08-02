/* OrderMonitor class stores the matrix that we execute operations upon, and makes sure to synchronize the threads that run simultaneously on the matrix. */
import java.util.Random;

public class OrderMonitor {

    private int A[][]; /* First matrix. */
    private int B[][]; /* Second matrix. */
    private int nextTurn; /* Counting which thread can access the matrix's through their ID. */

    public OrderMonitor(int n , int m , int p) { /* Constructor for the monitor with size parameters. */
        int nextLine = 0;
        nextTurn = 2;
        A = new int[n][m];
        B = new int[m][p];
        A = createAndPrint(A , n , m , 'A'); /* Filling up the matrices. */
        B = createAndPrint(B , m , p , 'B');
    }

    private static int[][] createAndPrint(int arr[][] , int rows , int columns , char which) {
        /* Fills up and prints a matrix with random ints through 0 to 9. */
        if (which == 'A') {
            System.out.println("\nA:");
        }
        else {
            System.out.println("B:");
        }
        int nextLine = 0;
        for (int i = 0 ; i <= rows - 1 ; i++) {
            for (int j = 0 ; j <= columns - 1 ; j++) {
                /* Running through the matrix and filling each cell with an int. */
                nextLine++;
                arr[i][j] = new Random().nextInt(10);
                System.out.print(arr[i][j] + "\t");
                if (nextLine == columns) {
                    nextLine = 0;
                    System.out.println("\n");
                }
            }
        }

        return arr;
    }

    public synchronized int multiply(int i , int j) {
        /* multiply runs through a chosen row and column of the thread, and calculates a scalar multiplication between the row from A and column from B. */
        int mult = 0;
        int row[] = this.A[i];
        for (int z = 0 ; z < row.length ; z++) { /* Running through the row and column. */
            mult += row[z] * B[z][j];
        }

        return mult;
    }

    public synchronized void waitForPrint(int threadID) throws InterruptedException {
        /* When a thread wants to print its product, it has to wait until all the previous threads behind it have finished printing their outcomes. */
        while (threadID >= nextTurn) { /* Waiting for this's turn to come. */
            wait();
        }
    }

    public synchronized void donePrinting() { /* A thread notifies it's done with printing its product, so we notify the waiting threads, and they wake up. */
        nextTurn++; /* Allowing the next thread (with next ID) to print its product. */
        notifyAll();
    }

    public synchronized int getRandomRow() { /* Returns a random number of A's row for the multiplication. */
        return new Random().nextInt(this.A.length);
    }

    public synchronized int getRandomColumn() { /* Returns a random number of B's column for the multiplication. */
        return new Random().nextInt(this.B[0].length);
    }

}
// End of class OrderMonitor.