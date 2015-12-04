package myshop.controllers;

import myshop.controllers.product.*;
import myshop.models.ProductJson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sergey on 30.11.15.
 */

/********************************************************************
 * PRODUCT MANAGEMENT                                *
 ********************************************************************/

@RestController
public class ProductController {
    // PRODUCT CREATE: Creates new product item in the product list
    // Set "name", "price", "quantity"
    @RequestMapping("product/create")
    public ProductJson productCreate(@RequestParam(value = "name", required = true) String name,
                                     @RequestParam(value = "price", required = true) double price,
                                     @RequestParam(value = "quantity", required = true) int quantity) {
        ProductCreate productProductCreate = new ProductCreate(name, price, quantity);
        return productProductCreate.productJson;
    }

    // PRODUCT READ: Returns product's attributes with given product's id - "id"
    @RequestMapping("product/read")
    public ProductJson productRead(@RequestParam(value = "id", required = true) int id) {
        ProductRead productRead = new ProductRead(id);
        return productRead.productJson;
    }

    // PRODUCT UPDATE: Updates product's attributes with given product's id
    @RequestMapping("product/update")
    public ProductJson productUpdate(@RequestParam(value = "id", required = true) int id,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "price", defaultValue = "-1", required = false) double price,
                                     @RequestParam(value = "quantity", defaultValue = "-1", required = false) int quantity) {
        ProductUpdate productUpdate = new ProductUpdate(id, name, price, quantity);
        return productUpdate.productJson;
    }

    // PRODUCT DELETE: Deletes product with given product's id
    @RequestMapping("product/delete")
    public ProductJson productDelete(@RequestParam(value = "id", required = true) int id) {
        ProductDelete productDelete = new ProductDelete(id);
        return productDelete.productJson;
    }

    // PRODUCT LIST: Lists sorted product for given "page" - page number, "rows" - number of rows,
    // "sortkey" - sorting key {nameasc, namedesc, priceasc, pricedesc}
    @RequestMapping("product/list")
    public ProductJson productList(@RequestParam(value = "page", required = true) int page,
                                   @RequestParam(value = "rows", required = true) int rows,
                                   @RequestParam(value = "sortkey", required = true) String sortkey) {
        ProductList productList = new ProductList(page, rows, sortkey);
        return productList.productJson;
    }

    // PRODUCT FILTER PRICE: Filters products in accordance with given parameters: "pricefrom" - price from, "priceto" - price to,
    // "page" - page number, "rows" - number of rows, "sortkey" - sorting key {price_asc, price_desc}
    @RequestMapping("product/filterprice")
    public ProductJson productFilterPrice(@RequestParam(value = "pricefrom", defaultValue = "0", required = false) int pricefrom,
                                          @RequestParam(value = "priceto", defaultValue = "-1", required = false) int priceto,
                                          @RequestParam(value = "page", required = true) int page,
                                          @RequestParam(value = "rows", required = true) int rows,
                                          @RequestParam(value = "sortkey", required = true) String sortkey) {
        ProductFilterPrice productFilterPrice = new ProductFilterPrice(pricefrom, priceto, page, rows, sortkey);
        return productFilterPrice.productJson;
    }

    // PRODUCT SEARCH: Searches products from catalog by matching the beginning of product name.
    // Filters results in accordance with given parameters: "pricefrom" - price from, "priceto" - price to,
    // "page" - page number, "rows" - number of rows, "sortkey" - sorting key {name_asc, name_desc, price_asc, price_desc}
    @RequestMapping("product/searchname")
    public ProductJson productSearchName(@RequestParam(value = "name", required = true) String name,
                                         @RequestParam(value = "pricefrom", defaultValue = "0", required = false) int pricefrom,
                                         @RequestParam(value = "priceto", defaultValue = "-1", required = false) int priceto,
                                         @RequestParam(value = "page", required = true) int page,
                                         @RequestParam(value = "rows", required = true) int rows,
                                         @RequestParam(value = "sortkey", required = true) String sortkey) {
        ProductSearchName productSearchName = new ProductSearchName(name, pricefrom, priceto, page, rows, sortkey);
        return productSearchName.productJson;
    }
}

