/* Product describes a product object that can be bought and has a name/price/etc. */
public class Product {

    private String productName; /* Name. */
    private double productPrice; /* Price. */

    public Product(String productName , double productPrice) {
    /* Regular builder with arguments for all attributes. */
        this.productName = new String (productName);
        this.productPrice = productPrice;
    }

    public Product(Product prod) { /* Copying builder. */
        this.productName = new String (prod.getProductName());
        this.productPrice = prod.getProductPrice();
    }

    public String getProductName() { /* Getter of the name. */
        return this.productName;
    }

    public double getProductPrice() { /* Getter of the price. */
        return this.productPrice;
    }
}
// End of class Product.