package myshop.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 03.12.15.
 */

public class UserJson {
    public String success;
    public List<User> users;

    public UserJson(){
        success = "False";
        users = new ArrayList<User>();
    }
}
