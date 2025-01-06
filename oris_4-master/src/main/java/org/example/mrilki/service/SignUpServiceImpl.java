package org.example.mrilki.service;

import org.example.mrilki.dto.SignUpForm;
import org.example.mrilki.models.User;
import org.example.mrilki.repositoris.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;

public class SignUpServiceImpl implements SignUpService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public SignUpServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void signUp(SignUpForm form) throws SQLException {
        User user = User.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .age(form.getAge())
                .password(passwordEncoder.encode(form.getPassword()))
                .username(form.getUsername())
                .build();
        userRepository.save(user);
    }

    @Override
    public boolean isUsernameExists(String username) {
        try {
            return userRepository.findByUsername(username).isPresent();
        } catch (SQLException e) {
            return false;
        }
    }


}
