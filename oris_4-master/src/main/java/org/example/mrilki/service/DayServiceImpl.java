package org.example.mrilki.service;

import org.example.mrilki.dto.DayDto;
import org.example.mrilki.models.Day;
import org.example.mrilki.repositoris.DayRepository;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DayServiceImpl implements DayService {

    private DayRepository dayRepository;

    public DayServiceImpl(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    @Override
    public void createDay(DayDto dto) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String text = dto.getText();
            Date date = simpleDateFormat.parse(dto.getDate());
            Long userId = dto.getUserId();
            dayRepository.save(Day.builder()
                    .text(text)
                    .date(date)
                    .userId(userId)
                    .build());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DayDto> getProfileDay(Long userId) throws SQLException {
        List<DayDto> days = new ArrayList<>();
        List<Day> daysUser = dayRepository.findByUser(userId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Day day : daysUser) {
            days.add(new DayDto(
                    day.getId(),
                    day.getText(),
                    simpleDateFormat.format(day.getDate()),
                    day.getUserId()
            ));
        }
        return days;
    }

    @Override
    public void updateDay(DayDto dto) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Long id = dto.getId();
            String text = dto.getText();

            Date date = simpleDateFormat.parse(dto.getDate());

            dayRepository.update(Day.builder()
                    .id(id)
                    .text(text)
                    .date(date)
                    .build());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<DayDto> getDay(Long Id) throws SQLException {
        Optional<Day> dayOptional = dayRepository.findById(Id);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (dayOptional.isPresent()) {
            Day day = dayOptional.get();
            return Optional.of(new DayDto(
                    day.getId(),
                    day.getText(),
                    simpleDateFormat.format(day.getDate()),
                    day.getUserId()
            ));
        }
        return Optional.empty();
    }

    @Override
    public DayDto getDayDtoLast(Long userId) throws SQLException {
        Day day = dayRepository.findByUserIdLast(userId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return DayDto.builder()
                .userId(userId)
                .text(day.getText())
                .id(day.getId())
                .date(simpleDateFormat.format(day.getDate()))
                .build();
    }


}
