package org.example.mrilki.repositoris;

import org.example.mrilki.dto.CommentDto;
import org.example.mrilki.models.Comment;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentRepositoryJdbcImpl implements CommentRepository {

    private static final String SQL_SELECT_ALL = "SELECT * FROM comment";
    private static final String SQL_SELECT_FROM_COMMENT_BY_DAY_ID = "SELECT * FROM comment WHERE day_id = ?";
    private static final String SQL_SELECT_FROM_COMMENT_BY_ID = "SELECT * FROM comment WHERE id = ?";
    private static final String SQL_INSERT_NEW_COMMENT = "INSERT INTO comment (text, day_id, user_id) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_COMMENT = "UPDATE comment SET text = ? WHERE id = ?";
    private static final String SQL_DELETE_COMMENT = "DELETE FROM comment WHERE id = ?";

    private DataSource dataSource;

    public CommentRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Optional<Comment> findById(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FROM_COMMENT_BY_ID);
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new Comment(resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("day_id")));
        }
        return Optional.empty();
    }

    @Override
    public List<Comment> findAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

        List<Comment> comments = new ArrayList<>();
        while (resultSet.next()) {
            Comment comment = new Comment(
                    resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("day_id")

            );
            comments.add(comment);
        }
        return comments;
    }

    @Override
    public void save(Comment entity) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_COMMENT);
        statement.setString(1, entity.getText());
        statement.setLong(2, entity.getDay_id());
        statement.setLong(3, entity.getAuthor_id());
        statement.executeUpdate();
    }

    @Override
    public void update(Comment entity) throws SQLException {
        Long commentId = entity.getId();
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_COMMENT);
        statement.setString(1, entity.getText());
        statement.setLong(2, commentId);
        statement.executeUpdate();
    }


    @Override
    public void remove(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_COMMENT);
        statement.setLong(1, id);
        statement.executeUpdate();
    }


    @Override
    public List<Comment> getCommentsByDay(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FROM_COMMENT_BY_DAY_ID);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        List<Comment> comments = new ArrayList<>();
        while (resultSet.next()) {
            comments.add(new Comment(
                    resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("day_id")

            ));
        }
        return comments;
    }
}

