package myshop.controllers.user;

import myshop.MySqlSettings;
import myshop.models.User;
import myshop.models.UserJson;

import java.sql.*;

// USER LIST: Lists sorted user for given "page" - page number, "rows" - number of rows, "sortkey" - sorting key {nameasc, namedesc}
public class UserList {
    public UserJson userJson;

    public UserList(int page, int rows, String sortkey) {
        userJson = new UserJson();

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
        String queryTemplate = "SELECT * FROM " + dbName + ".User ORDER BY " + sortRule + " LIMIT ?, ?;";

        Connection sqlConnection = null;
        PreparedStatement querySelect = null;
        ResultSet sqlResult = null;

        try {
            // Connect to MySQL server
            sqlConnection = DriverManager.getConnection(url, user, password);

            // Execute queries on MySQL server
            querySelect = sqlConnection.prepareCall(queryTemplate);
            querySelect.setInt(1, rowFrom);
            querySelect.setInt(2, rows);
            System.out.println(querySelect.toString());
            sqlResult = querySelect.executeQuery();

            while (sqlResult.next()) {
                userJson.users.add(new User(sqlResult.getInt("userId"),
                        sqlResult.getString("name"),
                        sqlResult.getBoolean("admin")));
                userJson.success = "True";
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


