package org.example.mrilki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProfileDto {
    Long id;
    String firstName;
    String lastName;
    int age;
    String username;
}
