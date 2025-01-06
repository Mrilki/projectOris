package org.example.mrilki.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private Long id;
    private String username;
    private String role;

}
