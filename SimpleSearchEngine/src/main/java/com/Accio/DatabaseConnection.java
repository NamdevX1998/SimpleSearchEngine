package com.Accio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        String user = "root";
        String pwd = "Aa1@namdev";
        String db = "simplesearchengine";
        return getConnection(user, pwd, db);
    }

    //method overloading
    private static Connection getConnection(String user, String pwd, String db) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pwd);
            //at this particular url we are set up the connection
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return connection;
    }
}
