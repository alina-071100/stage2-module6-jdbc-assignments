package jdbc;

import java.sql.Connection;

public class ConnectTest {
    public static void main(String[] args) {
        CustomConnector customConnector = new CustomConnector();
        customConnector.getConnection("jdbc:postgresql://localhost:5432/myfirstdb","postgres","postgres");
        SimpleJDBCRepository simpleJDBCRepository = new SimpleJDBCRepository();
        Connection connection = simpleJDBCRepository.getConnection();
        System.out.println(connection);
    }
}
