package Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconnection {

    private static Dbconnection dbconnection;
    private Connection connection;

    Dbconnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Dbconnection getInstance() {
        return (dbconnection == null )? dbconnection =new Dbconnection() : dbconnection;

    }
    public  Connection getConnection(){
        return connection;
    }

}
