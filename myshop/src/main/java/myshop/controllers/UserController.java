package myshop.controllers;

import myshop.controllers.user.*;
import myshop.models.UserJson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sergey on 30.11.15.
 */

/********************************************************************
 * USER MANAGEMENT                                   *
 ********************************************************************/

@RestController
public class UserController {

    // USER CREATE: Creates user with given name - "name" and rights - "admin"
    @RequestMapping("user/create")
    public UserJson userCreate(@RequestParam(value = "name", required = true) String name,
                               @RequestParam(value = "admin", required = false) boolean admin) {
        UserCreate userCreate = new UserCreate(name, admin);
        return userCreate.userJson;
    }

    // USER READ: Returns user's attributes with given user's id - "id"
    @RequestMapping("user/read")
    public UserJson userRead(@RequestParam(value = "id", required = true) int id) {
        UserRead userRead = new UserRead(id);
        return userRead.userJson;
    }

    // USER UPDATE: Updates user's attributes with given user's id
    @RequestMapping("user/update")
    public UserJson userUpdate(@RequestParam(value = "id", required = true) int id,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "admin", defaultValue = "null", required = false) String admin) {
        UserUpdate userUpdate = new UserUpdate(id, name, admin);
        return userUpdate.userJson;
    }

    // USER DELETE: Deletes user with given user's id
    @RequestMapping("user/delete")
    public UserJson userDelete(@RequestParam(value = "id", required = true) int id) {
        UserDelete userDelete = new UserDelete(id);
        return userDelete.userJson;
    }

    // USER LIST: Lists sorted user for given "page" - page number, "rows" - number of rows,
    // "sortkey" - sorting key {nameasc, namedesc}
    @RequestMapping("user/list")
    public UserJson userList(@RequestParam(value = "page", required = true) int page,
                             @RequestParam(value = "rows", required = true) int rows,
                             @RequestParam(value = "sortkey", required = true) String sortkey) {
        UserList userList = new UserList(page, rows, sortkey);
        return userList.userJson;
    }
}

