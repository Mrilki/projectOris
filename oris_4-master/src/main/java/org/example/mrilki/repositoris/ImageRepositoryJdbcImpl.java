package org.example.mrilki.repositoris;

import org.example.mrilki.models.Image;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ImageRepositoryJdbcImpl implements ImageRepository {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public ImageRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //language=SQL
    private final static String SQL_SELECT_BY_PARAM_ = "select * from image where original_file_name = ? and day_id = ? and size = ? order by id desc limit 1";
    //language=SQL
    private final static String SQL_INSERT = "insert into image(original_file_name, storage_file_name, type, day_id, size) " + "values (?, ?, ?, ?, ?)";
    //language=SQL
    private final static String SQL_SELECT_BY_ID = "select * from image where id = ?";
    //language=SQL
    private final static String SQL_SELECT_BY_DAY_ID = "select * from image where day_id = ?";
    //language=SQL
    private final static String SQL_DELETE = "delete from image where id = ?";

    private RowMapper<Image> fileRowMapper = (row, rowNumber) ->
            Image.builder()
                    .id(row.getLong("id"))
                    .originalFileName(row.getString("original_file_name"))
                    .storageFileName(row.getString("storage_file_name"))
                    .type(row.getString("type"))
                    .dayId(row.getLong("day_id"))
                    .size(row.getLong("size"))
                    .build();

    @Override
    public Optional<Image> findById(Long id) throws SQLException {
        Image image = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, fileRowMapper, id);
        return Optional.of(image);

    }

    @Override
    public List<Image> findAll() throws SQLException {
        return null;
    }

    @Override
    public void save(Image entity) {
        jdbcTemplate.update(SQL_INSERT, entity.getOriginalFileName(), entity.getStorageFileName(),
                entity.getType(),
                entity.getDayId(),
                entity.getSize());
    }

    @Override
    public void update(Image entity) throws SQLException {

    }


    @Override
    public void remove(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
        statement.setLong(1, id);
        statement.executeUpdate();
    }


    @Override
    public Optional<Image> findDayById(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_DAY_ID);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return Optional.of(Image.builder()
                    .id(resultSet.getLong("id"))
                    .originalFileName(resultSet.getString("original_file_name"))
                    .storageFileName(resultSet.getString("storage_file_name"))
                    .type(resultSet.getString("type"))
                    .dayId(resultSet.getLong("day_id"))
                    .size(resultSet.getLong("size"))
                    .build());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Image> findIdByImage(String originalName, Long dayId, Long size) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_PARAM_);
        statement.setString(1, originalName);
        statement.setLong(2, dayId);
        statement.setLong(3, size);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return Optional.of(Image.builder()
                    .id(resultSet.getLong("id"))
                    .originalFileName(resultSet.getString("original_file_name"))
                    .storageFileName(resultSet.getString("storage_file_name"))
                    .type(resultSet.getString("type"))
                    .dayId(resultSet.getLong("day_id"))
                    .size(resultSet.getLong("size"))
                    .build());
        }
        return Optional.empty();
    }


}
