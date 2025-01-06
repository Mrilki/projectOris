package org.example.mrilki.models;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
public class Comment {
    private long id;
    private String text;
    private long author_id;
    private long day_id;

}
