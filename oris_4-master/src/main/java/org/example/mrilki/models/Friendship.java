package org.example.mrilki.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Friendship {
    private Long id;
    private Long userId;
    private Long friendId;
    private String status;


}

