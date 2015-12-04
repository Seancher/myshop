package myshop.models;

/**
 * Created by sergey on 02.12.15.
 */
public class Product {
    private int id = 0;
    private String name = "ne";
    private double price = 23;
    private int quantity = 2;

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }
}
