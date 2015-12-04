package myshop.controllers.shoppingCart;

import myshop.MySqlSettings;
import myshop.models.ShoppingCart;
import myshop.models.ShoppingCartJson;

import java.sql.*;

// SHOPPING CART DELETE: Deletes shopping cart with given user's id, product's id
public class ShoppingCartDelete {
    public ShoppingCartJson shoppingCartJson;

    public ShoppingCartDelete(int userId, int productId) {
        shoppingCartJson = new ShoppingCartJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String queryTemplateSelectP = "SELECT * FROM " + dbName + ".Product WHERE productId = ?;";
        String queryTemplateSelectUP = "SELECT * FROM " + dbName + ".UserProduct WHERE userid = ? and productId = ?;";
        String queryTemplateDelete = "DELETE FROM " + dbName + ".UserProduct WHERE userId = ? and productId = ?;";

        Connection sqlConnection = null;
        PreparedStatement querySelect = null, queryDelete = null;
        ResultSet sqlResultP = null, sqlResultUP = null;

        try {
            // Connect to MySQL server
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

            queryDelete = sqlConnection.prepareStatement(queryTemplateDelete);
            queryDelete.setInt(1, userId);
            queryDelete.setInt(2, productId);
            queryDelete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sqlResultP != null) {
                    sqlResultP.close();
                }
                if (sqlResultUP != null) {
                    sqlResultUP.close();
                }
                if (querySelect != null) {
                    querySelect.close();
                }
                if (queryDelete != null) {
                    queryDelete.close();
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
