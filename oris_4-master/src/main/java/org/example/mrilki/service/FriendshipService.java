package org.example.mrilki.service;

import org.example.mrilki.dto.FriendDto;
import org.example.mrilki.dto.FriendshipDto;


import java.sql.SQLException;
import java.util.List;

public interface FriendshipService {
    void sendFriendRequest(FriendshipDto friendshipDto) throws SQLException;

    void acceptFriendRequest(FriendshipDto friendshipDto) throws SQLException;

    void rejectFriendRequest(FriendshipDto friendshipDto) throws SQLException;

    List<FriendDto> getLimitFriend(Long userId, int limit);

    List<FriendDto> getFriends(Long userId) throws SQLException;

    List<FriendDto> getPendingRequest(Long userId) throws SQLException;
}
