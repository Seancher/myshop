package myshop.controllers.product;

import myshop.MySqlSettings;
import myshop.models.Product;
import myshop.models.ProductJson;

import java.sql.*;

// PRODUCT DELETE: Deletes product's attributes with given product's id
public class ProductDelete {
    public ProductJson productJson;

    public ProductDelete(int id) {
        productJson = new ProductJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String queryTemplateSelect = "SELECT * FROM " + dbName + ".Product WHERE productId = ?;";
        String queryTemplateDelete = "DELETE FROM " + dbName + ".Product WHERE productId = ?;";

        Connection sqlConnection = null;
        PreparedStatement querySelect = null, queryDelete = null;
        ResultSet sqlResult = null;

        try {
            // Connect to MySQL server
            sqlConnection = DriverManager.getConnection(url, user, password);

            // Execute queries on MySQL server
            querySelect = sqlConnection.prepareCall(queryTemplateSelect);
            querySelect.setInt(1, id);
            sqlResult = querySelect.executeQuery();
            queryDelete = sqlConnection.prepareStatement(queryTemplateDelete);
            queryDelete.setInt(1, id);
            queryDelete.executeUpdate();

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
