import java.util.ArrayList;

/* Counter describes a store counter that creates a shopping invoice for the customer, stores money, calculates total price and can carry out the transaction and return exchange. */
public class Counter {

    private double counterAmount; /* Amount of money in the counter. */
    private ArrayList invoice; /* Customer's invoice made of an array of InvoiceLine objects. */
    private ArrayList products; /* Array of products to describe whatever the customer can purchase. */
    private final double BANANA = 7; /* Constant purchasable product prices. */
    private final double APPLE = 3;
    private final double WATERMELON = 18;
    private final double MILK = 5;
    private final double EGGS = 8;
    private final double BUTTER = 4;
    private final double BREAD = 6;
    private final double FISH = 25;
    private final double MEAT = 54;
    private final double ICECREAM = 15;


    public Counter() { /* Empty builder, sets attributes to default and sets the product list that are purchasable to store the products. */
        this.counterAmount = 0;
        this.invoice = new ArrayList();
        this.products = new ArrayList();
        this.products = this.whichProducts(this.products);
    }

    public Counter(double counterAmount) { /* Builder with amount to set the counter amount, sets product list to store purchasable products. */
        this.counterAmount = counterAmount;
        this.invoice = new ArrayList();
        this.products = new ArrayList();
        this.products = this.whichProducts(this.products);
    }

    public void addProduct(Product prod , int amount) {
    /* Adding a product to the invoice. If the product already appeared in the invoice we updated its amount in the correct line, else we add a new invoice line to the invoice.  */
        if (this.alreadyChosen(prod , amount) == false) {
            InvoiceLine line = new InvoiceLine(prod , amount);
            this.invoice.add(line);
        }
    }

    public String customerInvoice() { /* Returns a string that describes the customers invoice with product names, amounts and prices. */
        String strInvoice = new String(""); /* Empty string to add to. */
        for (int i = 0 ; i < this.invoice.size() ; i++) {
            strInvoice = strInvoice + ((InvoiceLine)this.invoice.get(i)).toString();
            /* Running through the invoice array and adding each invoice line using InvoiceLine toString. */
        }
        return strInvoice;
    }

    public String productsString() {
    /* Returns a string that describes the purchasable products in the store and their prices all numbered so the customer can choose what to buy. */
        String productsString = new String("");
        for (int i = 0 ; i < this.products.size() ; i++) { /* Running through the products array and adding its products and their prices to the string. */
            productsString = productsString + "\nNumber " + (i+1) + ": " + ((Product)this.products.get(i)).getProductName() + ", Price: " + ((Product)this.products.get(i)).getProductPrice();
        }
        return productsString;
    }

    public double customerTotalSum() { /* Calculates and returns the final price of the customer's invoice. */
        double sum = 0; /* Empty sum to add to. */
        for (int i = 0 ; i < this.invoice.size(); i++) { /* Running through the invoice and summing up each product's final price using getInvoiceLineSum. */
            sum = sum + ((InvoiceLine)this.invoice.get(i)).getInvoiceLineSum();
        }
        return sum;
    }

    public double paymentExchange(double payment) {
    /* Receives and amount that the customer is about to pay and calculates exchange to return to the customer by subtracting the invoice's price. */
        double exchange = payment - this.customerTotalSum();
        this.counterAmount = this.counterAmount + payment - exchange; /* Updating amount of money in the counter. */
        invoice.clear(); /* Customer's shopping is finished so we clear up the invoice array to assist the next customer. */
        return exchange;
    }

    public double getCounterAmount() { /* Getter for the amount of money in the counter. */
        return this.counterAmount;
    }

    public void setCounterAmount(double amount) { /* Setter for the amount pf money in the counter using received parameter. */
        this.counterAmount = this.counterAmount + amount;
    }

    public String getProductName(int i) { /* Getter for a specific product name in the purchasable product list using index parameter. */
        return ((Product)this.products.get(i)).getProductName();
    }

    public double getProductPrice(int i) { /* Getter for a specific product price in the purchasable product list using index parameter. */
        return ((Product)this.products.get(i)).getProductPrice();
    }

    private ArrayList whichProducts(ArrayList products) {
    /* Utility function that adds 10 products and their prices to the purchasable product list, used for the Main function that runs the counter. */
        products = new ArrayList();
        Product banana = new Product("Banana" , BANANA); /* Creating the product. */
        products.add(banana); /* Adding the created product. */
        Product apple = new Product("Apple" , APPLE);
        products.add(apple);
        Product watermelon = new Product("Watermelon" , WATERMELON);
        products.add(watermelon);
        Product milk = new Product("Milk" , MILK);
        products.add(milk);
        Product eggs = new Product("Eggs" , EGGS);
        products.add(eggs);
        Product butter = new Product("Butter" , BUTTER);
        products.add(butter);
        Product bread = new Product("Bread" , BREAD);
        products.add(bread);
        Product fish = new Product("Fish" , FISH);
        products.add(fish);
        Product meat = new Product("Meat" , MEAT);
        products.add(meat);
        Product iceCream = new Product ("IceCream" , ICECREAM);
        products.add(iceCream);
        return products;
    }

    private boolean alreadyChosen(Product prod , int amount) {
    /* Utility function that checks whether a product (and its amount that we want to add) is already
    appearing in the customer's invoice, if it does, we add it by changing its amount in the invoice */
        for (int i = 0 ; i < this.invoice.size(); i++) { /* Running through the invoice and comparing product names. */
            if (prod.getProductName().compareTo(((InvoiceLine)this.invoice.get(i)).getInvoiceLineProduct().getProductName()) == 0) {
                ((InvoiceLine)this.invoice.get(i)).setInvoiceLineAmount(amount); /* If the product was found we update its amount. */
                return true;
            }
        }
        return false;
    }

}
// End of class Counter.