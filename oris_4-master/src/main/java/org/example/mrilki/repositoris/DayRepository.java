package org.example.mrilki.repositoris;

import org.example.mrilki.models.Day;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface DayRepository extends CrudRepository<Day> {
    List<Day> findByUser(Long id) throws SQLException;

    Day findByUserIdLast(Long id) throws SQLException;


}
