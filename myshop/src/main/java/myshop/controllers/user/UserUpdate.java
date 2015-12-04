package myshop.controllers.user;

import myshop.MySqlSettings;
import myshop.models.User;
import myshop.models.UserJson;

import java.sql.*;

// USER UPDATE: Updates user's attributes with given user's id
public class UserUpdate {
    public UserJson userJson;

    public UserUpdate(int id, String name, String admin) {
        userJson = new UserJson();

        // MySQL Connection Settings
        String url = "jdbc:mysql://" + MySqlSettings.host + ":" + MySqlSettings.port + "/myshop";
        String dbName = MySqlSettings.database;
        String user = MySqlSettings.user;
        String password = MySqlSettings.password;
        String queryTemplateUpdateName = "UPDATE " + dbName + ".User SET name = ? WHERE userId = ?;";
        String queryTemplateUpdateAdmin = "UPDATE " + dbName + ".User SET admin = ? WHERE userId = ?;";
        String queryTemplateSelect = "SELECT * FROM " + dbName + ".User WHERE userId = ?;";

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
            System.out.println("Chernov" + admin.equals("true"));
            System.out.println(admin);
            System.out.println(queryTemplateUpdateAdmin);
            if (admin.equals("true") || admin.equals("false")) { // Update admin field
                System.out.println(admin);
                System.out.println(queryTemplateUpdateAdmin);
                queryUpdate = sqlConnection.prepareCall(queryTemplateUpdateAdmin);
                queryUpdate.setBoolean(1, Boolean.parseBoolean(admin));
                queryUpdate.setInt(2, id);
                queryUpdate.executeUpdate();
            }

            querySelect = sqlConnection.prepareCall(queryTemplateSelect);
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
