package myshop.controllers.user;

import myshop.MySqlSettings;
import myshop.models.User;
import myshop.models.UserJson;

import java.sql.*;

// USER CREATE: Creates user with given name - "name" and rights - "admin"
public class UserCreate {
    public UserJson userJson;

    public UserCreate(String name, boolean admin) {
        userJson = new UserJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String queryTemplateCreate = "INSERT INTO " + dbName + ".User (userId, name, admin) " +
                "VALUES (null, ?, ?);";
        String queryTemplateSelect = "SELECT * FROM " + dbName + ".User WHERE name = ?;";

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
            queryCreate.setBoolean(2, admin);
            queryCreate.executeUpdate();
            querySelect = sqlConnection.prepareStatement(queryTemplateSelect);
            querySelect.setString(1, name);
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