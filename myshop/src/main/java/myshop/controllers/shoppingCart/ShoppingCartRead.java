package myshop.controllers.shoppingCart;

import myshop.MySqlSettings;
import myshop.models.ShoppingCart;
import myshop.models.ShoppingCartJson;

import java.sql.*;

// SHOPPING CART READ: Returns shopping cart for user - "userid", product - "productid"
public class ShoppingCartRead {

    public ShoppingCartJson shoppingCartJson;

    public ShoppingCartRead(int userId, int productId) {
        shoppingCartJson = new ShoppingCartJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String queryTemplateSelectP = "SELECT * FROM " + dbName + ".Product WHERE productId = ?;";
        String queryTemplateSelectUP = "SELECT * FROM " + dbName + ".UserProduct WHERE userid = ? and productId = ?;";

        Connection sqlConnection = null;
        PreparedStatement querySelect = null;
        ResultSet sqlResultP = null, sqlResultUP = null;

        try {
            // Connect to MySQL Server
            sqlConnection = DriverManager.getConnection(url, user, password);

            // Execute queries on MySQL server
            querySelect = sqlConnection.prepareStatement(queryTemplateSelectP);
            querySelect.setInt(1, productId);
            sqlResultP = querySelect.executeQuery();
            querySelect = sqlConnection.prepareStatement(queryTemplateSelectUP);
            querySelect.setInt(1, userId);
            querySelect.setInt(2, productId);
            sqlResultUP = querySelect.executeQuery();
            if (sqlResultP.next() && sqlResultUP.next()) {
                shoppingCartJson.products.add(new ShoppingCart(userId, productId,
                        sqlResultP.getString("name"),
                        sqlResultP.getDouble("price"),
                        sqlResultUP.getInt("quantity")));
                shoppingCartJson.success = "True";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (querySelect != null) {
                    querySelect.close();
                }
                if (sqlConnection != null) {
                    sqlConnection.close();
                }
                if (sqlResultP != null) {
                    sqlResultP.close();
                }
                if (sqlResultUP != null) {
                    sqlResultUP.close();
                }

            } catch (SQLException ex) {
                System.out.println("Fault");
            }
        }
    }
}

