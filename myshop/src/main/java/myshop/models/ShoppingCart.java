package myshop.models;

/**
 * Created by sergey on 03.12.15.
 */
public class ShoppingCart {
    private int userId = 0;
    private int productId = 0;
    private String name = "";
    private double price = 0;
    private int quantity = 0;

    public ShoppingCart(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public ShoppingCart(int userId, int productId, String name, double price, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
