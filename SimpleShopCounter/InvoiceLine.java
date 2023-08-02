/*InvoiceLine describes an object that stores a Product, its amount and the total price - an invoice line. */
public class InvoiceLine {

    private Product prod; /* Product of the invoice line. */
    private int amount; /* Amount of the product. */
    private double totalProdSum; /* Price sum for the product. */

    public InvoiceLine(Product prod , int amount) {
    /* Regular builder with arguments for attributes, total sum is calculated using them. */
        this.amount = amount;
        this.prod = new Product(prod);
        this.totalProdSum = amount * this.prod.getProductPrice();
    }

    public double getInvoiceLineSum() { /* Getter for price sum. */
        return this.totalProdSum;
    }

    public Product getInvoiceLineProduct() { /* Getter for product. */
        return this.prod;
    }

    public void setInvoiceLineAmount(int amount) { /* Set the amount of the product we want to buy. */
        this.amount = this.amount + amount;
        this.totalProdSum = this.amount * this.prod.getProductPrice();
    }

    public String toString () { /* Print the InvoiceLine object as an invoice line. */
        return "Product name: " + this.prod.getProductName() + " , " + "amount: "
                + this.amount + " , " + "total price: " + this.totalProdSum + "\n";
    }
}
// End of class InvoiceLine.