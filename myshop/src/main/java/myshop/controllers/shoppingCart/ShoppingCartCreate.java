package myshop.controllers.shoppingCart;

import myshop.MySqlSettings;
import myshop.models.ShoppingCart;
import myshop.models.ShoppingCartJson;

import java.sql.*;

// SHOPPING CART CREATE: Creates shopping cart for user - "userid", product - "productid", quantity - "quantity"
public class ShoppingCartCreate {
    public ShoppingCartJson shoppingCartJson;

    public ShoppingCartCreate(int userId, int productId, int quantity) {
        shoppingCartJson = new ShoppingCartJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String queryTemplateCreate = "INSERT INTO " + dbName + ".UserProduct (userId, productId, quantity) " +
                "VALUES (?, ?, ?);";
        String queryTemplateSelect = "SELECT * FROM " + dbName + ".Product WHERE productId = ?;";

        Connection sqlConnection = null;
        PreparedStatement queryCreate = null;
        PreparedStatement querySelect = null;
        ResultSet sqlResult = null;

        try {
            // Connect to MySQL Server
            sqlConnection = DriverManager.getConnection(url, user, password);

            // Execute queries on MySQL server
            queryCreate = sqlConnection.prepareStatement(queryTemplateCreate);
            queryCreate.setInt(1, userId);
            queryCreate.setInt(2, productId);
            queryCreate.setInt(3, quantity);
            queryCreate.executeUpdate();
            querySelect = sqlConnection.prepareStatement(queryTemplateSelect);
            querySelect.setInt(1, productId);
            sqlResult = querySelect.executeQuery();
            if (sqlResult.next()) {
                shoppingCartJson.products.add(new ShoppingCart(userId, productId,
                        sqlResult.getString("name"),
                        sqlResult.getDouble("price"),
                        quantity));
                shoppingCartJson.success = "True";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (queryCreate != null) {
                    queryCreate.close();
                }
                if (querySelect != null) {
                    querySelect.close();
                }
                if (sqlConnection != null) {
                    sqlConnection.close();
                }
                if (sqlResult != null) {
                    sqlResult.close();
                }

            } catch (SQLException ex) {
                System.out.println("Fault");
            }
        }
    }
}
