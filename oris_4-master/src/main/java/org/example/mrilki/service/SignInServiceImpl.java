package org.example.mrilki.service;

import org.example.mrilki.dto.SignInForm;
import org.example.mrilki.dto.UserDto;
import org.example.mrilki.models.User;
import org.example.mrilki.repositoris.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.Optional;

public class SignInServiceImpl implements SignInService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public SignInServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDto singIn(SignInForm signInForm) throws SQLException {
        Optional<User> userOptional = userRepository.findByUsername(signInForm.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (checkPassword(signInForm.getPassword(), user.getPassword())) {
                return UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .role(user.getRole())
                        .build();
            }
        }
        return null;
    }

    @Override
    public Boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
