/* MatrixThread class represents the thread that executes various operations upon the int matrix in the monitor. */
public class MatrixThread extends Thread {

    private OrderMonitor monitor; /* The monitor that stores the matrix that we use. */
    private int threadID; /* Number that represents each thread. */

    public MatrixThread(int a , OrderMonitor b) { /* Constructor with parameters. */
        this.threadID = a;
        this.monitor = b;
    }

    @Override
    public void run() { /* Runs the thread. */
        super.run();
        int row = monitor.getRandomRow(); /* Choosing a random row and column of the matrix. */
        int column = monitor.getRandomColumn();
        int mult = this.monitor.multiply(row , column); /* Executing a scalar multiplication between the column and the row we have chosen from the matrix. */
        try { /* After the operation, printing the outcomes of the multiplications by an order of the thread's ID's. */
            this.monitor.waitForPrint(this.threadID);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Indexes: " + row + ", " + column + " - " + "Thread " + this.threadID + ": " + mult);
        monitor.donePrinting(); /* Notifying the monitor that the operation is over. */
    }

}
// End of class MatrixThread.