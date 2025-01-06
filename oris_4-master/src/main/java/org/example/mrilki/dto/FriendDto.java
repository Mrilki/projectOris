package org.example.mrilki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FriendDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
}
