package org.example.mrilki.service;

import org.example.mrilki.dto.ReportDto;

import java.sql.SQLException;
import java.util.List;

public interface ReportService {
    void acceptedReport(Long id) throws SQLException;

    void rejectedReport(Long id) throws SQLException;

    void createReport(ReportDto report) throws SQLException;

    List<ReportDto> getReports() throws SQLException;

}
