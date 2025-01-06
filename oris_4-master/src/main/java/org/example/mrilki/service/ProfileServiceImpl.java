package org.example.mrilki.service;

import org.example.mrilki.dto.ProfileDto;
import org.example.mrilki.models.User;
import org.example.mrilki.repositoris.UserRepository;

import java.sql.SQLException;
import java.util.Optional;

public class ProfileServiceImpl implements ProfileService {

    private UserRepository userRepository;

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public ProfileDto getProfile(String username) throws SQLException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ProfileDto.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .age(user.getAge())
                    .username(user.getUsername())
                    .build();
        }
        return null;
    }

    @Override
    public ProfileDto getProfileById(Long id) throws SQLException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ProfileDto.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .age(user.getAge())
                    .username(user.getUsername())
                    .build();
        }
        return null;
    }

    @Override
    public void updateProfile(ProfileDto profileDto) throws SQLException {
        User user = User.builder()
                .firstName(profileDto.getFirstName())
                .lastName(profileDto.getLastName())
                .age(profileDto.getAge())
                .username(profileDto.getUsername())
                .build();

        userRepository.update(user);
    }


}
