package myshop.controllers.user;

import myshop.MySqlSettings;
import myshop.models.User;
import myshop.models.UserJson;

import java.sql.*;

// USER READ: Returns user's attributes with given user's id - "id"
public class UserRead {
    public UserJson userJson;

    public UserRead(int id) {
        userJson = new UserJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String queryTemplate = "SELECT * FROM " + dbName + ".User WHERE userId = ?;";

        Connection sqlConnection = null;
        PreparedStatement querySelect = null;
        ResultSet sqlResult = null;

        try {
            // Connect to MySQL server
            sqlConnection = DriverManager.getConnection(url, user, password);

            // Execute queries on MySQL server
            querySelect = sqlConnection.prepareCall(queryTemplate);
            querySelect.setInt(1, id);
            sqlResult = querySelect.executeQuery();

            if (sqlResult.next()) {
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

