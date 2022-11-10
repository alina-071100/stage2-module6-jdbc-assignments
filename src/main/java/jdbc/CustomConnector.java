package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class CustomConnector {
    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    public Connection connection;

    public Connection getConnection(String url) {

        try {
            Class.forName(DRIVER_CLASS_NAME);
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnection(String url, String user, String password){
        try {
            CustomDataSource customDataSource = CustomDataSource.getInstance();
            connection = customDataSource.getConnection(url, user, password);
        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }
}