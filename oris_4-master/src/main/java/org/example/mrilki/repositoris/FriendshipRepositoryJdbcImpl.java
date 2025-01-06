package org.example.mrilki.repositoris;

import org.example.mrilki.models.Friendship;
import org.example.mrilki.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendshipRepositoryJdbcImpl implements FriendshipRepository {

    private static final String SQL_SELECT_ALL_REQUEST = "SELECT * FROM friendships WHERE friend_id = ? and status = 'PENDING'";
    private static final String SQL_SELECT_ALL = "SELECT * FROM friendships";
    private static final String SQL_SELECT_ALL_FRIENDS = "SELECT * FROM friendships WHERE (user_id = ? OR friend_id = ?) AND status = 'ACCEPTED'";
    private static final String SQL_SELECT_ALL_FRIENDS_WITH_LIMIT = "SELECT * FROM friendships WHERE (user_id = ? OR friend_id = ?) AND status = 'ACCEPTED' LIMIT ?";
    private static final String SQL_SELECT_FROM_FRIENDSHIP_BY_ID = "SELECT * FROM friendships WHERE id = ?";
    private static final String SQL_INSERT_NEW_FRIENDSHIP = "INSERT INTO friendships (user_id, friend_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_FRIENDSHIP = "UPDATE friendships SET user_id = ?, friend_id = ?, status = ? WHERE id= ?";
    private static final String SQL_DELETE_FRIENDSHIP = "DELETE FROM friendships WHERE id = ?";
    private static final String SQL_SELECT_ALL_FRIENDSHIPS_BY_USER = "SELECT * FROM friendships WHERE user_id = ? AND friend_id = ? OR friend_id = ? AND user_id = ?";

    private DataSource dataSource;

    public FriendshipRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<Friendship> getFriends(Long userId) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FRIENDS);
        statement.setLong(1, userId);
        statement.setLong(2, userId);
        ResultSet resultSet = statement.executeQuery();
        List<Friendship> friends = new ArrayList<>();
        while (resultSet.next()) {
            friends.add(new Friendship(
                    resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("friend_id"),
                    resultSet.getString("status")
            ));
        }
        return friends;
    }

    @Override
    public List<Friendship> getFriendsLimit(Long userId, int limit) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FRIENDS_WITH_LIMIT);
        statement.setLong(1, userId);
        statement.setLong(2, userId);
        statement.setInt(3, limit);
        ResultSet resultSet = statement.executeQuery();
        List<Friendship> friends = new ArrayList<>();
        while (resultSet.next()) {
            friends.add(new Friendship(
                    resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("friend_id"),
                    resultSet.getString("status")
            ));
        }
        return friends;
    }

    @Override
    public List<Friendship> getPendingRequests(Long userId) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_REQUEST);
        statement.setLong(1, userId);
        ResultSet resultSet = statement.executeQuery();
        List<Friendship> friends = new ArrayList<>();
        while (resultSet.next()) {
            friends.add(new Friendship(
                    resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("friend_id"),
                    resultSet.getString("status")
            ));
        }
        return friends;
    }

    @Override
    public Optional<Friendship> getFriendshipByUser(Long user_id, Long friend_id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FRIENDSHIPS_BY_USER);
        statement.setLong(1, user_id);
        statement.setLong(2, friend_id);
        statement.setLong(3, user_id);
        statement.setLong(4, friend_id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(Friendship.builder().
                    id(resultSet.getLong("id")).
                    userId(resultSet.getLong("user_id")).
                    friendId(resultSet.getLong("friend_id")).
                    status(resultSet.getString("status")).
                    build());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Friendship> findById(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FROM_FRIENDSHIP_BY_ID);
        statement.setLong(1, id);


        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new Friendship(resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("friend_id"),
                    resultSet.getString("status")));
        }
        return Optional.empty();
    }


    @Override
    public List<Friendship> findAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

        List<Friendship> result = new ArrayList<>();

        while (resultSet.next()) {
            Friendship friendship = new Friendship(resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("friend_id"),
                    resultSet.getString("status"));
            result.add(friendship);
        }
        return result;
    }

    @Override
    public void save(Friendship entity) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_FRIENDSHIP);
        statement.setLong(1, entity.getUserId());
        statement.setLong(2, entity.getFriendId());
        statement.executeUpdate();
    }

    @Override
    public void update(Friendship entity) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_FRIENDSHIP);
        statement.setLong(1, entity.getUserId());
        statement.setLong(2, entity.getFriendId());
        statement.setString(3, entity.getStatus());
        statement.setLong(4, entity.getId());
        statement.executeUpdate();
    }


    @Override
    public void remove(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_FRIENDSHIP);
        statement.setLong(1, id);
        statement.executeUpdate();

    }
}
