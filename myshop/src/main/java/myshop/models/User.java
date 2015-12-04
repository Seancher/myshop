package myshop.models;

/**
 * Created by sergey on 03.12.15.
 */
public class User {
    private int id = 0;
    private String name = "ne";
    private boolean admin = false;

    public User(int id, String name, boolean admin) {
        this.id = id;
        this.name = name;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getAdmin() {
        return admin;
    }
}