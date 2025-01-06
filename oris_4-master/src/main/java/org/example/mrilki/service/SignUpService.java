package org.example.mrilki.service;

import org.example.mrilki.dto.SignUpForm;

import java.sql.SQLException;

public interface SignUpService {

    void signUp(SignUpForm form) throws SQLException;
    boolean isUsernameExists(String username);

}
