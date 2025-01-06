package org.example.mrilki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.mrilki.models.Day;

@Builder
@Data
@AllArgsConstructor
public class ReportDto {
    private long id;
    private String text;
    private String reason;
    private long dayId;
    private Day day;
}
