package myshop.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 03.12.15.
 */
public class ShoppingCartJson {
    public String success;
    public List<ShoppingCart> products;

    public ShoppingCartJson(){
        success = "False";
        products = new ArrayList<ShoppingCart>();
    }
}