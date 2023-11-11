package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {

    public static void main(String... arg) throws SQLException {
        ConnectionPool connectionPool = new ConnectionPool("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://127.0.0.1:3306/vakeel_test", "root", "admin", 5, 10, true);

        Connection con = connectionPool.getConnection();
        System.out.println("We have got connection from ConnectionPool class");

        PreparedStatement prepStmt = con.prepareStatement("select * from students");

        ResultSet rs = prepStmt.executeQuery();
        while (rs.next()) {
            System.out.print(rs.getInt("ID") + " ");
            System.out.println(rs.getString("NAME"));
        }

        if (rs != null)
            rs.close(); // close resultSet
        if (prepStmt != null)
            prepStmt.close(); // close PreparedStatement

        connectionPool.free(con);
        System.out.println("We have free/released connection to ConnectionPool class");
    }


}
