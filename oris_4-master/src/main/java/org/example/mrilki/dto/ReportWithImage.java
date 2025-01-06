package org.example.mrilki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.mrilki.models.Image;

@Builder
@Data
@AllArgsConstructor
public class ReportWithImage {
    ReportDto report;
    Image image;
}
