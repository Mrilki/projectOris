package org.example.mrilki.models;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
@AllArgsConstructor
public class Image {
    private Long id;
    private String originalFileName;
    private String storageFileName;
    private Long size;
    private Long dayId;
    private String type;
}
