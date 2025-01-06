package org.example.mrilki.repositoris;

import org.example.mrilki.models.Day;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DayRepositoryJdbcImpl implements DayRepository {
    private static final String SQL_SELECT_FROM_DAY_BY_ID = "SELECT * FROM day WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM day";
    private static final String SQL_SELECT_FROM_DAY_BY_USER = "SELECT * FROM day WHERE user_id = ? ORDER BY date DESC";
    private static final String SQL_SELECT_FROM_DAY_BY_USER_LAST = "SELECT * FROM day WHERE user_id = ? ORDER BY id DESC LIMIT 1";
    private static final String SQL_INSERT_NEW_DAY = "INSERT INTO day (text, date, user_id) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_DAY = "UPDATE day SET text = ?, date = ? WHERE id = ?";
    private static final String SQL_DELETE_DAY = "DELETE FROM day WHERE id = ?";

    private DataSource dataSource;

    public DayRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Day> findByUser(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FROM_DAY_BY_USER);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        List<Day> days = new ArrayList<>();
        while (resultSet.next()) {
            days.add(new Day(
                    resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getDate("date"),
                    resultSet.getLong("user_id")
            ));
        }
        return days;
    }

    @Override
    public Day findByUserIdLast(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FROM_DAY_BY_USER_LAST);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return Day.builder()
                    .id(resultSet.getLong("id"))
                    .text(resultSet.getString("text"))
                    .date(resultSet.getDate("date"))
                    .userId(resultSet.getLong("user_id"))
                    .build();
        }
        return null;
    }

    @Override
    public List<Day> findAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);


        List<Day> days = new ArrayList<>();

        while (resultSet.next()) {
            Day day = new Day(resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getDate("date"),
                    resultSet.getLong("user_id"));
            days.add(day);
        }
        return days;
    }

    @Override
    public Optional<Day> findById(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FROM_DAY_BY_ID);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return Optional.of(new Day(resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getDate("date"),
                    resultSet.getLong("user_id")));
        }
        return Optional.empty();
    }

    @Override
    public void save(Day entity) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_DAY);
        statement.setString(1, entity.getText());
        statement.setDate(2, new java.sql.Date(entity.getDate().getTime()));
        statement.setLong(3, entity.getUserId());
        statement.execute();
    }

    @Override
    public void update(Day entity) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_DAY);
        statement.setString(1, entity.getText());
        statement.setDate(2, new java.sql.Date(entity.getDate().getTime()));
        statement.setLong(3, entity.getId());
        statement.executeUpdate();
    }


    @Override
    public void remove(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_DAY);
        statement.setLong(1, id);
        statement.executeUpdate();
    }


}
