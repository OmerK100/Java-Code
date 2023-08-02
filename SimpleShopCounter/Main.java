import java.util.Scanner;

/* Main class runs a program that simulates a store that allows the customer to make a purchase and complete a transaction using the Counter, InvoiceLine and Product classes we made. */
public class Main {

    public static void main(String[] args) { /* main function. */

        System.out.println("---------------Counter turning on---------------");
        Scanner scan = new Scanner(System.in);
        Counter counter = new Counter(); /* Counter for the program. */
        boolean lastCustomer = false; /* Running a loop while there's still a customer that wants to make a purchase. */
        do {
            System.out.println("Welcome to the counter, here you can pay for the products you purchase.");
            int i = 0;
            double j = 0;
            Product prod;
            String keepGoing;
            do {
                System.out.println("Would you like to add a product? If you do, type \"yes\" else \"no\".");
                keepGoing = scan.nextLine();
                if (keepGoing.compareTo("no") == 0) { /* Customer's cart is ready for payment. */
                    if (counter.customerTotalSum() != 0) {
                        System.out.println("Your final invoice is:");
                        System.out.println(counter.customerInvoice());
                        if (counter.customerTotalSum() > 1000000) {
                            System.out.println("Shopping at the price of a Ferrari, what are you making??");
                            /* Cool Easter Egg for people spending more than a million. */
                        }
                        System.out.println("Final price: " + counter.customerTotalSum()); /* Printing invoice and total sum. */
                        System.out.println("\nHow much will you be paying?");
                        /* Gathering payment from customer, making sure there's enough money in the counter
                        by adding it and completing the transaction (also clearing the invoice for the next customer).  */
                        j = scan.nextDouble();
                        counter.setCounterAmount(2 * j);
                        System.out.println("Your exchange is: " + counter.paymentExchange(j));
                    } else {
                        System.out.println("Cart is empty, have a nice day!"); /* If no product was chosen (total price is 0) we notify. */
                    }
                } else { /* Customer wants to add another product to his cart. */
                    prod = next(counter);
                    /* Gathering the product that the customer wants by showing him the product list from Counter class and letting him type in the product he wants. */
                    System.out.println("Choose amount of the product:"); /* Gathering the amount of the product that the customer wants. */
                    i = scan.nextInt();
                    counter.addProduct(prod, i); /* Adding the product and its amount to the invoice. */
                    System.out.println("\nYour current list: \n" + counter.customerInvoice()); /* Showing the current cart to the customer. */
                    scan.skip("\\R?"); /* skip function helps us to fix ignorance that might occur due to nextLine function reading \n signs. */
                }
            } while (keepGoing.compareTo("yes") == 0); /* Running a loop while the customer still wants to add more products to his cart. */
            System.out.println("\nAre you the last customer? If you are, type \"yes\" else \"no\". Have a nice day!");
            /* After the customer finished his purchase we check whether he was the last one in line. */
            scan.skip("\\R?");
            keepGoing = scan.nextLine();
            if (keepGoing.compareTo("no") == 0) {
                lastCustomer = false;
            } else {
                lastCustomer = true;
            }
        } while (lastCustomer == false); /* If there are still more customers we open an invoice for the next one, else we end the program. */
        System.out.println("---------------Counter turning off---------------");
    }

    public static Product next(Counter counter) {
        /* Utility program to show the customer the purchasable product list and allow him to choose the product he wants by typing its number. */
        System.out.println("Type in the number next to the product in order to choose it:");
        System.out.println(counter.productsString() + "\n"); /* Showing the customer the available products for purchase. */
        Scanner scan = new Scanner(System.in);
        int i = scan.nextInt();
        String productName = counter.getProductName(i - 1);
        double price = counter.getProductPrice(i - 1);
        Product prod = new Product(productName, price); /* Creating a product from the gathered string name and price customer has chosen. */
        return prod;
    }
}
// End of class Main.