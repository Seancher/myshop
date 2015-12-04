package myshop.controllers.shoppingCart;

import myshop.MySqlSettings;
import myshop.models.ShoppingCart;
import myshop.models.ShoppingCartJson;

import java.sql.*;

// SHOPPING CART LIST: Lists sorted shopping carts given "userid" - userid,
// "page" - page number, "rows" - number of rows,
// "sortkey" - sorting key {priceasc, pricedesc, nameasc, namedesc}
public class ShoppingCartList {
    public ShoppingCartJson shoppingCartJson;

    public ShoppingCartList(int userId, int page, int rows, String sortkey) {
        shoppingCartJson = new ShoppingCartJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String sortRule = "";
        int rowFrom = (page - 1) * rows;
        if (sortkey.equals("nameasc")) {
            sortRule = "Product.name ASC";
        } else if (sortkey.equals("namedesc")) {
            sortRule = "Product.name DESC";
        } else if (sortkey.equals("priceasc")) {
            sortRule = "Product.price ASC";
        } else if (sortkey.equals("pricedesc")) {
            sortRule = "Product.price DESC";
        }
        String queryTemplate =
                "SELECT UserProduct.userId, UserProduct.productId, " +
                        "Product.name, Product.price, UserProduct.quantity " +
                        "FROM " + dbName + ".UserProduct " +
                        "LEFT JOIN " + dbName + ".Product " +
                        "ON UserProduct.productId = Product.productId " +
                        "WHERE UserProduct.userId = ? " +
                        "ORDER BY " + sortRule + " LIMIT ?, ?;";

        Connection sqlConnection = null;
        PreparedStatement querySelect = null;
        ResultSet sqlResult = null;

        try {
            // Connect to MySQL server
            sqlConnection = DriverManager.getConnection(url, user, password);

            // Execute queries on MySQL server
            querySelect = sqlConnection.prepareCall(queryTemplate);
            querySelect.setInt(1, userId);
            querySelect.setInt(2, rowFrom);
            querySelect.setInt(3, rows);
            System.out.println(querySelect.toString());
            sqlResult = querySelect.executeQuery();

            while (sqlResult.next()) {
                shoppingCartJson.products.add(new ShoppingCart(userId,
                        sqlResult.getInt("productId"),
                        sqlResult.getString("name"),
                        sqlResult.getDouble("price"),
                        sqlResult.getInt("quantity")));
                shoppingCartJson.success = "True";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sqlResult != null) {
                    sqlResult.close();
                }
                if (querySelect != null) {
                    querySelect.close();
                }
                if (sqlConnection != null) {
                    sqlConnection.close();
                }

            } catch (SQLException ex) {
                System.out.println("Fault");
            }
        }
    }
}

