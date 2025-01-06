package org.example.mrilki.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignUpForm {
    private String firstName;
    private String lastName;
    private int age;
    private String password;
    private String username;

}
