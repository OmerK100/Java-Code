/* Item class represents an item in the menu that consists of description (name, etc), its type and its price. */
public class Item {

    private String description; /* Attributes */
    private String type;
    private double price;

    public Item (String description , String type , double price) { /* Builder with parameters. */
        this.description = new String(description);
        this.type = new String(type);
        this.price = price;
    }

    public String getDescription() { /* Getters. */
        return this.description;
    }

    public String getType() {
        return this.type;
    }

    public double getPrice() {
        return this.price;
    }

}
/* End of class Item. */