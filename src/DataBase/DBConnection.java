package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String pass = "admin";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
        }return conn;
    }
}