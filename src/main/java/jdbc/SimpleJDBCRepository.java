package jdbc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleJDBCRepository {

    private Connection connection = null;
    private PreparedStatement ps = null;
    private Statement st = null;

    public SimpleJDBCRepository(Connection connection) {
        this.connection = connection;
    }

    private static final String createUserSQL = "insert into myuser(id, firstName, lastName, age) values (?, ?, ?, ?)";
    private static final String updateUserSQL = "update myuser set firstName = ?, lastName = ?, age = ? where id = ?";
    private static final String deleteUser = "delete from myuser where id = ?";
    private static final String findUserByIdSQL = "select * from myuser where id = ?";
    private static final String findUserByNameSQL = "select * from myuser where firstname = ?";
    private static final String findAllUserSQL = "select * from myuser";


    public Long createUser(User user) {
        Long id = null;

        try {
            ps = connection.prepareStatement(createUserSQL);
            ps.setLong(1, user.getId());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setInt(4, user.getAge());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }


    public User findUserById(Long userId) {
        User user = null;
        try {
            ps = connection.prepareStatement(findUserByIdSQL);
            ps.setLong(1, userId);
            ResultSet resultSet = ps.executeQuery();

            user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getInt("age"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User findUserByName(String userName) {
        User user = null;
        try {
            ps = connection.prepareStatement(findUserByNameSQL);
            ps.setString(1, userName);
            ResultSet resultSet = ps.executeQuery();

            user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getInt("age")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> findAllUser() {
        List<User> users = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(findAllUserSQL);

            users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("age")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User updateUser(User user) {
        try {
            ps = connection.prepareStatement(updateUserSQL);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getFirstName());
            ps.setInt(3, user.getAge());
            ps.setLong(4, user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void deleteUser(Long userId) {
        try {
            ps = connection.prepareStatement(deleteUser);
            ps.setLong(1, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}