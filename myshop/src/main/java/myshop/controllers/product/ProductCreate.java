package myshop.controllers.product;

import myshop.MySqlSettings;
import myshop.models.Product;
import myshop.models.ProductJson;

import java.sql.*;

// PRODUCT CREATE: Creates new product item in the product list
// Set "name", "price", "quantity"
public class ProductCreate {
    public ProductJson productJson;

    public ProductCreate(String name, double price, int quantity) {
        productJson = new ProductJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String queryTemplateCreate = "INSERT INTO " + dbName + ".Product (productId, name, price, quantity) " +
                "VALUES (null, ?, ?, ?);";
        String queryTemplateSelect = "SELECT * FROM " + dbName + ".Product WHERE name = ?;";

        Connection sqlConnection = null;
        PreparedStatement queryCreate = null;
        PreparedStatement querySelect = null;
        ResultSet sqlResult = null;

        try {
            // Connect to MySQL Server
            sqlConnection = DriverManager.getConnection(url, user, password);

            // Execute queries on MySQL server
            queryCreate = sqlConnection.prepareStatement(queryTemplateCreate);
            queryCreate.setString(1, name);
            queryCreate.setDouble(2, price);
            queryCreate.setInt(3, quantity);
            queryCreate.executeUpdate();
            querySelect = sqlConnection.prepareStatement(queryTemplateSelect);
            querySelect.setString(1, name);
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