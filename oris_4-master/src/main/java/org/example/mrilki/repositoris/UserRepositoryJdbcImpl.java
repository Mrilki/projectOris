package org.example.mrilki.repositoris;

import org.example.mrilki.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {


    private static final String SQL_SELECT_FROM_USERS_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM users";
    private static final String SQL_SELECT_FROM_USERS_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_INSERT_NEW_USER = "INSERT INTO users (first_name, last_name, age, password, username) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, age = ? WHERE username = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";

    private DataSource dataSource;

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;

    }


    @Override
    public Optional<User> findByUsername(String username) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FROM_USERS_BY_USERNAME);
        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new User(resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("age"),
                    resultSet.getString("password"),
                    resultSet.getString("role")));
        }

        return Optional.empty();
    }


    @Override
    public Optional<User> findById(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FROM_USERS_BY_ID);
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();


        if (resultSet.next()) {
            return Optional.of(new User(resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("age"),
                    resultSet.getString("password"),
                    resultSet.getString("role")));
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

        List<User> result = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User(resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("age"),
                    resultSet.getString("password"),
                    resultSet.getString("role"));
            result.add(user);
        }

        return result;
    }


    @Override
    public void save(User entity) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_USER);
        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getLastName());
        statement.setInt(3, entity.getAge());
        statement.setString(4, entity.getPassword());
        statement.setString(5, entity.getUsername());
        statement.executeUpdate();
    }

    @Override
    public void update(User entity) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER);
        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getLastName());
        statement.setInt(3, entity.getAge());
        statement.setString(4, entity.getUsername());
        statement.executeUpdate();
    }


    @Override
    public void remove(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

}
