package myshop.controllers.shoppingCart;

import myshop.MySqlSettings;
import myshop.models.ShoppingCart;
import myshop.models.ShoppingCartJson;

import java.sql.*;

// SHOPPING CART UPDATE: Updates quantity with given user's id, product's id
public class ShoppingCartUpdate {
    public ShoppingCartJson shoppingCartJson;

    public ShoppingCartUpdate(int userId, int productId, int quantity) {
        shoppingCartJson = new ShoppingCartJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String queryTemplateUpdateQuantity = "UPDATE " + dbName + ".UserProduct SET quantity = ? WHERE userId = ? and productId = ?;";
        String queryTemplateSelectP = "SELECT * FROM " + dbName + ".Product WHERE productId = ?;";
        String queryTemplateSelectUP = "SELECT * FROM " + dbName + ".UserProduct WHERE userid = ? and productId = ?;";

        Connection sqlConnection = null;
        PreparedStatement queryUpdate = null, querySelect = null;
        ResultSet sqlResultP = null, sqlResultUP = null;

        try {
            // Connect to MySQL server
            sqlConnection = DriverManager.getConnection(url, user, password);

            // Execute queries on MySQL server
            if (quantity >= 0) {
                queryUpdate = sqlConnection.prepareCall(queryTemplateUpdateQuantity);
                queryUpdate.setInt(1, quantity);
                queryUpdate.setInt(2, userId);
                queryUpdate.setInt(3, productId);
                queryUpdate.executeUpdate();
            }

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
                if (queryUpdate != null) {
                    queryUpdate.close();
                }
                if (querySelect != null) {
                    querySelect.close();
                }
                if (sqlResultP != null) {
                    sqlResultP.close();
                }
                if (sqlResultUP != null) {
                    sqlResultUP.close();
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

