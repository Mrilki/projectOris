package org.example.mrilki.service;

import org.example.mrilki.dto.DayDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DayService {
    void createDay(DayDto dto) throws SQLException;

    List<DayDto> getProfileDay(Long userId) throws SQLException;

    void updateDay(DayDto dto) throws SQLException;

    Optional<DayDto> getDay(Long id) throws SQLException;

    DayDto getDayDtoLast(Long userId) throws SQLException;
}
