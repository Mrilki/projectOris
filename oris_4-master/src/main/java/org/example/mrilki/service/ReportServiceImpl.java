package org.example.mrilki.service;

import org.example.mrilki.dto.ReportDto;
import org.example.mrilki.models.Day;
import org.example.mrilki.models.Report;
import org.example.mrilki.repositoris.DayRepository;
import org.example.mrilki.repositoris.ReportRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportServiceImpl implements ReportService {

    DayRepository dayRepository;
    ReportRepository reportRepository;

    public ReportServiceImpl(DayRepository dayRepository, ReportRepository reportRepository) {
        this.dayRepository = dayRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public void acceptedReport(Long id) throws SQLException {
        Optional<Report> reportOptional = reportRepository.findById(id);
        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();
            dayRepository.remove(report.getDayId());
        }
        reportRepository.remove(id);
    }

    @Override
    public void rejectedReport(Long id) throws SQLException {
        reportRepository.remove(id);
    }

    @Override
    public void createReport(ReportDto report) throws SQLException {
        reportRepository.save(Report.builder()
                .id(report.getId())
                .text(report.getText())
                .reason(report.getReason())
                .dayId(report.getDayId())
                .build());
    }

    @Override
    public List<ReportDto> getReports() throws SQLException {
        List<ReportDto> reportDtos = new ArrayList<>();
        List<Report> reports = reportRepository.findAll();
        for (Report report : reports) {
            Optional<Day> dayOptional = dayRepository.findById(report.getDayId());
            if (dayOptional.isPresent()) {
                reportDtos.add(new ReportDto(
                        report.getId(),
                        report.getText(),
                        report.getReason(),
                        report.getDayId(),
                        dayOptional.get()
                ));
            }

        }
        return reportDtos;
    }
}
