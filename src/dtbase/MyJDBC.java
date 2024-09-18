package dtbase;

import java.sql.*;

public class MyJDBC {
    static Connection con;

    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/traincontracts",
                    "root", "123456");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}