package myshop.controllers.product;

import myshop.MySqlSettings;
import myshop.models.Product;
import myshop.models.ProductJson;

import java.sql.*;

import static java.lang.Double.MAX_VALUE;

// PRODUCT SEARCH: Searches products from the catalog by matching the beginning of a product name.
// Filters results in accordance with given parameters: "pricefrom" - price from, "priceto" - price to,
// "page" - page number, "rows" - number of rows, "sortkey" - sorting key {name_asc, name_desc, price_asc, price_desc}
public class ProductSearchName {
    public ProductJson productJson;

    public ProductSearchName(String name, double pricefrom, double priceto, int page, int rows, String sortkey) {
        productJson = new ProductJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String sortRule = "";
        int rowFrom = (page - 1) * rows;
        if (sortkey.equals("nameasc")) {
            sortRule = "name ASC";
        } else if (sortkey.equals("namedesc")) {
            sortRule = "name DESC";
        } else if (sortkey.equals("priceasc")) {
            sortRule = "price ASC";
        } else if (sortkey.equals("pricedesc")) {
            sortRule = "price DESC";
        }
        String queryTemplate = "SELECT * FROM " + dbName + ".Product WHERE name LIKE ? " +
                "and price >= ? and price <= ? ORDER BY " + sortRule + " LIMIT ?, ?;";
        priceto = (priceto == -1) ? MAX_VALUE : priceto;

        Connection sqlConnection = null;
        PreparedStatement querySelect = null;
        ResultSet sqlResult = null;

        try {
            // Connect to MySQL server
            sqlConnection = DriverManager.getConnection(url, user, password);

            // Execute queries on MySQL server
            querySelect = sqlConnection.prepareCall(queryTemplate);
            querySelect.setString(1, name + "%");
            querySelect.setDouble(2, pricefrom);
            querySelect.setDouble(3, priceto);
            querySelect.setInt(4, rowFrom);
            querySelect.setInt(5, rows);
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
