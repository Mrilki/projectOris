package org.example.mrilki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class DayDto {
    private Long id;
    private String text;
    private String date;
    private Long userId;
}
