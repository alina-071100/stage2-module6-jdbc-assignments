package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;


public class CustomConnector {
    public Connection getConnection(String url) {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/myfirstdb", "postgres", "postgres");
            CustomDataSource customDataSource = CustomDataSource.getInstance();
            connection = customDataSource.getConnection(url);
        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }

    public Connection getConnection(String url, String user, String password){
        Connection connection = null;

        try {
            CustomDataSource customDataSource = CustomDataSource.getInstance();
            connection = customDataSource.getConnection(url, user, password);
        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }
}