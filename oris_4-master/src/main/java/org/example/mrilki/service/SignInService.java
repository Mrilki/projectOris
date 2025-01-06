package org.example.mrilki.service;

import org.example.mrilki.dto.SignInForm;
import org.example.mrilki.dto.UserDto;

import java.sql.SQLException;

public interface SignInService {

    UserDto singIn(SignInForm signInForm) throws SQLException;

    Boolean checkPassword(String rawPassword, String encodedPassword);
}
