package org.example.mrilki.models;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Data
@Builder
public class Day {
    private Long id;
    private String text;
    private Date date;
    private Long userId;

}
