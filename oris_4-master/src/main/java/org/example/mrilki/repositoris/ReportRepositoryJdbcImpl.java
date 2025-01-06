package org.example.mrilki.repositoris;

import org.example.mrilki.models.Report;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportRepositoryJdbcImpl implements ReportRepository {
    private static final String SQL_SELECT_FROM_REPORT_BY_ID = "SELECT * FROM report WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM report";
    private static final String SQL_INSERT_NEW_REPORT = "INSERT INTO report (text, reason, day_id) VALUES (?, ?, ?)";
    private static final String SQL_DELETE_REPORT = "DELETE FROM report WHERE id = ?";

    private DataSource dataSource;

    public ReportRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Report> findAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

        List<Report> reports = new ArrayList<>();

        while (resultSet.next()) {
            Report report = new Report(resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getString("reason"),
                    resultSet.getLong("day_id"));
            reports.add(report);
        }
        return reports;
    }


    @Override
    public Optional<Report> findById(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FROM_REPORT_BY_ID);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return Optional.of(new Report(resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getString("reason"),
                    resultSet.getLong("day_id")));
        }
        return Optional.empty();
    }

    @Override
    public void save(Report entity) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_REPORT);
        statement.setString(1, entity.getText());
        statement.setString(2, entity.getReason());
        statement.setLong(3, entity.getDayId());
        statement.executeUpdate();
    }

    @Override
    public void update(Report entity) throws SQLException {

    }


    @Override
    public void remove(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_REPORT);
        statement.setLong(1, id);
        statement.executeUpdate();

    }

}
