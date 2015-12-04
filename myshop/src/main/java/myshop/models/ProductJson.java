package myshop.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 03.12.15.
 */
public class ProductJson {
    public String success;
    public List<Product> products;

    public ProductJson(){
        success = "False";
        products = new ArrayList<Product>();
    }
}
