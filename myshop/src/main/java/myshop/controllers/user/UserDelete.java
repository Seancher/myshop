package myshop.controllers.user;

import myshop.MySqlSettings;
import myshop.models.User;
import myshop.models.UserJson;

import java.sql.*;

// USER DELETE: Deletes user with given user's id
public class UserDelete {
    public UserJson userJson;

    public UserDelete(int id) {
        userJson = new UserJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String queryTemplateSelect = "SELECT * FROM " + dbName + ".User WHERE userId = ?;";
        String queryTemplateDelete = "DELETE FROM " + dbName + ".User WHERE userId = ?;";

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
