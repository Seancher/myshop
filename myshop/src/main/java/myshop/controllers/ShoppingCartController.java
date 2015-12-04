package myshop.controllers;

import myshop.controllers.shoppingCart.*;
import myshop.models.ShoppingCartJson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sergey on 30.11.15.
 */

/********************************************************************
 * SHOPPING CART MANAGEMENT                          *
 ********************************************************************/

@RestController
public class ShoppingCartController {
    // SHOPPING CART CREATE: Creates shopping cart for user - "userid", product - "productid", quantity - "quantity"
    @RequestMapping("shoppingcart/create")
    public ShoppingCartJson shoppingCartCreate(@RequestParam(value = "userid", required = true) int userId,
                                               @RequestParam(value = "productid", required = true) int productId,
                                               @RequestParam(value = "quantity", required = true) int quantity) {
        ShoppingCartCreate shoppingCartCreate = new ShoppingCartCreate(userId, productId, quantity);
        return shoppingCartCreate.shoppingCartJson;
    }

    // SHOPPING CART READ: Returns shopping cart for user - "userid", product - "productid"
    @RequestMapping("shoppingcart/read")
    public ShoppingCartJson shoppingCartRead(@RequestParam(value = "userid", required = true) int userId,
                                             @RequestParam(value = "productid", required = true) int productId) {
        ShoppingCartRead shoppingCartRead = new ShoppingCartRead(userId, productId);
        return shoppingCartRead.shoppingCartJson;
    }

    // SHOPPING CART UPDATE: Updates quantity with given user's id, product's id
    @RequestMapping("shoppingcart/update")
    public ShoppingCartJson shoppingCartUpdate(@RequestParam(value = "userid", required = true) int userId,
                                               @RequestParam(value = "productid", required = true) int productId,
                                               @RequestParam(value = "quantity", required = true) int quantity) {
        ShoppingCartUpdate shoppingCartUpdate = new ShoppingCartUpdate(userId, productId, quantity);
        return shoppingCartUpdate.shoppingCartJson;
    }

    // SHOPPING CART DELETE: Deletes shopping cart with given user's id, product's id
    @RequestMapping("shoppingcart/delete")
    public ShoppingCartJson shoppingCartDelete(@RequestParam(value = "userid", required = true) int userId,
                                               @RequestParam(value = "productid", required = true) int productId) {
        ShoppingCartDelete shoppingCartDelete = new ShoppingCartDelete(userId, productId);
        return shoppingCartDelete.shoppingCartJson;
    }

    // SHOPPING CART LIST: Lists sorted shopping carts given "userid" - userid, "page" - page number,
    // "rows" - number of rows, "sortkey" - sorting key {priceasc, pricedesc, nameasc, namedesc}
    @RequestMapping("shoppingcart/list")
    public ShoppingCartJson shoppingCartList(@RequestParam(value = "userid", required = true) int userId,
                                             @RequestParam(value = "page", required = true) int page,
                                             @RequestParam(value = "rows", required = true) int rows,
                                             @RequestParam(value = "sortkey", required = true) String sortkey) {
        ShoppingCartList shoppingCartList = new ShoppingCartList(userId, page, rows, sortkey);
        return shoppingCartList.shoppingCartJson;
    }

    // SHOPPING CART PURCHASE: Purchases a product for given user - "userid" and given product "productid"
    @RequestMapping("shoppingcart/purchase")
    public ShoppingCartJson shoppingCartPurchase(@RequestParam(value = "userid", required = true) int userId,
                                                 @RequestParam(value = "productid", required = true) int productId) {
        ShoppingCartPurchase shoppingCartPurchase = new ShoppingCartPurchase(userId, productId);
        return shoppingCartPurchase.shoppingCartJson;
    }
}

