package org.example.mrilki.repositoris;

import org.example.mrilki.models.Day;
import org.example.mrilki.models.Image;

import java.sql.SQLException;
import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image> {
    Optional<Image> findDayById(Long id) throws SQLException;

    Optional<Image> findIdByImage(String originalName, Long dayId, Long size) throws SQLException;

}
