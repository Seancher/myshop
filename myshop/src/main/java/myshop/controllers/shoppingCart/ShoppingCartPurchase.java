package myshop.controllers.shoppingCart;

import myshop.MySqlSettings;
import myshop.models.ShoppingCart;
import myshop.models.ShoppingCartJson;

import java.sql.*;

// SHOPPING CART PURCHASE: Purchases a product for given user - "userid" and given product "productid"
public class ShoppingCartPurchase {
    public ShoppingCartJson shoppingCartJson;

    public ShoppingCartPurchase(int userId, int productId) {
        shoppingCartJson = new ShoppingCartJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;

        String queryTemplateSelectQuantity = "SELECT (SELECT quantity FROM " + dbName + ".UserProduct " +
                "WHERE userId = ? and productId = ?) AS quantityUP, " +
                "(SELECT quantity FROM " + dbName + ".Product WHERE productId = ?) as quantityP;";

        String queryTemplateDelete = "DELETE FROM " + dbName + ".UserProduct WHERE userId = ? and productId = ?;";
        String queryTemplateUpdate = "UPDATE " + dbName + ".Product SET quantity = ? WHERE productId = ?;";
        String queryTemplateSelect = "SELECT * FROM " + dbName + ".Product WHERE productId = ?;";

        Connection sqlConnection = null;
        PreparedStatement querySelectQuantity = null;
        PreparedStatement queryDelete = null;
        PreparedStatement queryUpdate = null;
        PreparedStatement quertySelect = null;
        ResultSet sqlResult = null;
        try {
            // Connect to MySQL Server
            sqlConnection = DriverManager.getConnection(url, user, password);

            // Execute queries on MySQL server
            querySelectQuantity = sqlConnection.prepareStatement(queryTemplateSelectQuantity);
            querySelectQuantity.setInt(1, userId);
            querySelectQuantity.setInt(2, productId);
            querySelectQuantity.setInt(3, productId);
            sqlResult = querySelectQuantity.executeQuery();
            sqlResult.next();
            int quantityP = sqlResult.getInt("quantityP");
            int quantityUP = sqlResult.getInt("quantityUP");
            System.out.println("quantityP " + quantityP);
            System.out.println("quantityUP " + quantityUP);

            if (quantityP >= quantityUP) {
                queryDelete = sqlConnection.prepareStatement(queryTemplateDelete);
                queryDelete.setInt(1, userId);
                queryDelete.setInt(2, productId);
                queryDelete.executeUpdate();

                queryUpdate = sqlConnection.prepareStatement(queryTemplateUpdate);
                queryUpdate.setInt(1, quantityP - quantityUP);
                queryUpdate.setInt(2, productId);
                queryUpdate.executeUpdate();

                quertySelect = sqlConnection.prepareStatement(queryTemplateSelect);
                quertySelect.setInt(1, productId);
                sqlResult = quertySelect.executeQuery();
                if (sqlResult.next()) {
                    shoppingCartJson.products.add(new ShoppingCart(userId, productId,
                            sqlResult.getString("name"),
                            sqlResult.getDouble("price"),
                            quantityUP));
                    shoppingCartJson.success = "True";
                }
            } else {
                shoppingCartJson.success = "False";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (querySelectQuantity != null) {
                    querySelectQuantity.close();
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
