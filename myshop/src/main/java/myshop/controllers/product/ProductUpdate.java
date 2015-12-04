package myshop.controllers.product;

import myshop.MySqlSettings;
import myshop.models.Product;
import myshop.models.ProductJson;

import java.sql.*;

// PRODUCT UPDATE: Updates product's attributes with given product's id
public class ProductUpdate {
    public ProductJson productJson;

    public ProductUpdate(int id, String name, double price, int quantity) {
        productJson = new ProductJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String queryTemplateUpdateName = "UPDATE " + dbName + ".Product SET name = ? WHERE productId = ?;";
        String queryTemplateUpdatePrice = "UPDATE " + dbName + ".Product SET price = ? WHERE productId = ?;";
        String queryTemplateUpdateQuantity = "UPDATE " + dbName + ".Product SET quantity = ? WHERE productId = ?;";
        String queryTemplateSelect = "SELECT * FROM " + dbName + ".Product WHERE productId = ?;";

        Connection sqlConnection = null;
        PreparedStatement queryUpdate = null, querySelect = null;
        ResultSet sqlResult = null;

        try {
            // Connect to MySQL server
            sqlConnection = DriverManager.getConnection(url, user, password);

            // Execute queries on MySQL server
            if (name != null) { // Update Name
                queryUpdate = sqlConnection.prepareCall(queryTemplateUpdateName);
                queryUpdate.setString(1, name);
                queryUpdate.setInt(2, id);
                queryUpdate.executeUpdate();
            }
            if (price > 0) { // Update Price
                queryUpdate = sqlConnection.prepareCall(queryTemplateUpdatePrice);
                queryUpdate.setDouble(1, price);
                queryUpdate.setInt(2, id);
                queryUpdate.executeUpdate();
            }
            if (quantity >= 0) { // Update Quantity
                queryUpdate = sqlConnection.prepareCall(queryTemplateUpdateQuantity);
                queryUpdate.setInt(1, quantity);
                queryUpdate.setInt(2, id);
                queryUpdate.executeUpdate();
            }

            querySelect = sqlConnection.prepareCall(queryTemplateSelect);
            querySelect.setInt(1, id);
            sqlResult = querySelect.executeQuery();

            if (sqlResult.next()) {
                productJson.products.add(new Product(sqlResult.getInt("productId"),
                        sqlResult.getString("name"),
                        sqlResult.getDouble("price"),
                        sqlResult.getInt("quantity")));
                productJson.success = "True";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sqlResult != null) {
                    sqlResult.close();
                }
                if (queryUpdate != null) {
                    queryUpdate.close();
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

