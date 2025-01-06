package org.example.mrilki.models;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class User {

    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private int age;
    private String password;
    private String role;


}
