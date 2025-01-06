package org.example.mrilki.models;

import lombok.*;


@AllArgsConstructor
@Data
@Builder
public class Report {
    private long id;
    private String text;
    private String reason;
    private long dayId;

}
