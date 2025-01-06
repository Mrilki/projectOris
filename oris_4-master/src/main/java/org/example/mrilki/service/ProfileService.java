package org.example.mrilki.service;

import org.example.mrilki.dto.ProfileDto;

import java.sql.SQLException;

public interface ProfileService {
    ProfileDto getProfile(String username) throws SQLException;

    ProfileDto getProfileById(Long id) throws SQLException;

    void updateProfile(ProfileDto profileDto) throws SQLException;
}
