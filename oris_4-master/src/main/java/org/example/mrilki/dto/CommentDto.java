package org.example.mrilki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String text;
    private Long day_id;
    private Long user_id;
    private String author;
}
