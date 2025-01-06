package org.example.mrilki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mrilki.models.Image;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayWithImage {
    DayDto day;
    Image image;
}
