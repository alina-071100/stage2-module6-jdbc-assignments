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
     private final CustomDataSource dataSource = CustomDataSource.getInstance();

    private Connection connection = null;
    private PreparedStatement ps = null;
    private Statement st = null;

    public SimpleJDBCRepository(Connection connection){
        this.connection = connection;
    }

    private static final String CREATE_USER_SQL  = "insert into myusers(id, firstName, lastName, age) values (?, ?, ?, ?)";
    private static final String  UPDATE_USER_SQL = "update myusers set firstName = ?, lastName = ?, age = ? where id = ?";
    private static final String DELETE_USER = "delete from myusers where id = ?";
    private static final String FIND_USER_BY_ID_SQL = "select * from myusers where id = ?";
    private static final String FIND_USER_BY_NAME_SQL = "select * from myusers where firstname = ?";
    private static final String FIND_ALL_USER_SQL = "select * from myusers";

    private static final String FIRSTNAME = "firstName";
    private static final String LASTNAME = "lastName";


    public Long createUser(User user){
        Long id = null;

        try{
            ps = connection.prepareStatement(CREATE_USER_SQL);
            ps.setLong(1, user.getId());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setInt(4, user.getAge());
            ps.execute();
            id=user.getId();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return id ;
    }

    public User findUserById(Long userId) {
        User user = null;
        try{
            ps = connection.prepareStatement(FIND_USER_BY_ID_SQL);
            ps.setLong(1, userId);
            ResultSet resultSet = ps.executeQuery();

            user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString(FIRSTNAME),
                    resultSet.getString(LASTNAME),
                    resultSet.getInt("age"));
        }catch (SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    public User findUserByName(String userName){
        User user = null;
        try {
            ps = connection.prepareStatement(FIND_USER_BY_NAME_SQL);
            ps.setString(1, userName);
            ResultSet resultSet = ps.executeQuery();

            user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString(FIRSTNAME),
                    resultSet.getString(LASTNAME),
                    resultSet.getInt("age")
            );
        } catch (SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    public List<User> findAllUser(){
        List<User> users = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(FIND_ALL_USER_SQL);

            users = new ArrayList<>();
            while (resultSet.next()){
                users.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString(FIRSTNAME),
                        resultSet.getString(LASTNAME),
                        resultSet.getInt("age")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    public User updateUser(User user){
        try {
            ps = connection.prepareStatement(UPDATE_USER_SQL);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getFirstName());
            ps.setInt(3, user.getAge());
            ps.setLong(4, user.getId());
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void deleteUser(Long userId) {
        try{
            ps = connection.prepareStatement(DELETE_USER);
            ps.setLong(1, userId);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}