package Dal;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author xuant
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBcontext {

    protected Connection connection;

    public DBcontext() {
        try {
            String url = "jdbc:sqlserver://THANH\\XUANTHANH;databaseName=t1;;encrypt=false;trustServerCertificate=false;loginTimeout=30;";
            String username = "sa";
            String password = "1121212";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }
}
