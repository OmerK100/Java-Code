/* Order class represents a customers order that was created through the Menu object and stores the customer's details and invoice. */
import java.io.FileWriter;
import java.io.IOException;

public class Order {

    private String name; /* Customer details attributes. */
    private String ID;
    private String order; /* Customer's order with Items chosen, amounts and total price. */

    public Order(String name , String ID , String order) { /* Builder with parameters. */
        this.name = new String(name);
        this.ID = new String(ID);
        this.order = new String(order);
    }

    public void createOrderFile() { /* createOrderFile creates a .txt file that represents the Order by copying its attributes. */
        if (ID != null && name != null) {
            try {
                FileWriter fw = new FileWriter(name + ".txt"); /* Writing all the attributes to a new .txt file named after the customer. */
                fw.write("Order Receipt");
                fw.write("\n");
                fw.write("\n");
                fw.write("Name: " + name);
                fw.write("\n");
                fw.write("ID: " + ID);
                fw.write("\n");
                fw.write("\n");
                fw.write(order);
                fw.close();
            }
            catch(IOException e) { /* An exception might occur while creating the file. */
                System.out.println("Error");
            }
        }
    }

}
/* End of class Order. */