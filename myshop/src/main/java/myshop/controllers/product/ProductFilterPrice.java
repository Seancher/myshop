package myshop.controllers.product;

import myshop.MySqlSettings;
import myshop.models.Product;
import myshop.models.ProductJson;

import java.sql.*;

import static java.lang.Double.MAX_VALUE;

// PRODUCT FILTER PRICE: Filters products in accordance with given parameters:
// "pricefrom" - price from, "priceto" - price to, "page" - page number,
// "rows" - number of rows, "sortkey" - sorting key {price_asc, price_desc}
public class ProductFilterPrice {
    public ProductJson productJson;

    public ProductFilterPrice(double pricefrom, double priceto, int page, int rows, String sortkey) {
        productJson = new ProductJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String sortRule = "";
        int rowFrom = (page - 1) * rows;
        if (sortkey.equals("priceasc")) {
            sortRule = "price ASC";
        } else if (sortkey.equals("pricedesc")) {
            sortRule = "price DESC";
        }
        String queryTemplate = "SELECT * FROM " + dbName + ".Product " +
                "WHERE price >= ? and price <= ? ORDER BY " + sortRule + " LIMIT ?, ?;";
        priceto = (priceto == -1) ? MAX_VALUE : priceto;

        Connection sqlConnection = null;
        PreparedStatement querySelect = null;
        ResultSet sqlResult = null;

        try {
            // Connect to MySQL server
            sqlConnection = DriverManager.getConnection(url, user, password);

            // Execute queries on MySQL server
            querySelect = sqlConnection.prepareCall(queryTemplate);
            querySelect.setDouble(1, pricefrom);
            querySelect.setDouble(2, priceto);
            querySelect.setInt(3, rowFrom);
            querySelect.setInt(4, rows);
            System.out.println(querySelect.toString());
            sqlResult = querySelect.executeQuery();

            while (sqlResult.next()) {
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
                if (sqlConnection != null) {
                    sqlConnection.close();
                }

            } catch (SQLException ex) {
                System.out.println("Fault");
            }
        }
    }

}
