package org.example.mrilki.service;

import org.example.mrilki.dto.FriendDto;
import org.example.mrilki.dto.FriendshipDto;
import org.example.mrilki.models.Friendship;
import org.example.mrilki.models.User;
import org.example.mrilki.repositoris.FriendshipRepository;
import org.example.mrilki.repositoris.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendshipServiceImpl implements FriendshipService {

    private FriendshipRepository friendshipRepository;
    private UserRepository userRepository;

    public FriendshipServiceImpl(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void sendFriendRequest(FriendshipDto friendshipDto) throws SQLException {
        Optional<Friendship> friendshipOptional = friendshipRepository.getFriendshipByUser(friendshipDto.getUserId(), friendshipDto.getFriendId());
        if (friendshipOptional.isPresent()) {
            Friendship friendship = friendshipOptional.get();
            if (friendship.getStatus().equals("PENDING")) {
                friendshipRepository.update(Friendship.builder()
                        .userId(friendshipDto.getUserId())
                        .friendId(friendshipDto.getFriendId())
                        .status("ACCEPTED")
                        .build());
            }
        } else {
            friendshipRepository.save(Friendship.builder()
                    .userId(friendshipDto.getUserId())
                    .friendId(friendshipDto.getFriendId())
                    .status("PENDING")
                    .build());
        }

    }

    @Override
    public void acceptFriendRequest(FriendshipDto friendshipDto) throws SQLException {
        Optional<Friendship> friendshipOptional = friendshipRepository.getFriendshipByUser(friendshipDto.getUserId(), friendshipDto.getFriendId());
        if (friendshipOptional.isPresent()) {
            Friendship friendship = friendshipOptional.get();
            if (friendship.getStatus().equals("PENDING")) {
                friendshipRepository.update(Friendship.builder()
                        .id(friendship.getId())
                        .userId(friendshipDto.getUserId())
                        .friendId(friendshipDto.getFriendId())
                        .status("ACCEPTED")
                        .build());
            }
        }
    }

    @Override
    public void rejectFriendRequest(FriendshipDto friendshipDto) throws SQLException {
        Optional<Friendship> friendshipOptional = friendshipRepository.getFriendshipByUser(friendshipDto.getUserId(), friendshipDto.getFriendId());
        if (friendshipOptional.isPresent()) {
            Friendship friendship = friendshipOptional.get();
            friendshipRepository.remove(friendship.getId());
        }
    }

    @Override
    public List<FriendDto> getLimitFriend(Long userId, int limit) {
        return null;
    }

    @Override
    public List<FriendDto> getFriends(Long userId) throws SQLException {
        List<FriendDto> allFriends = new ArrayList<>();
        List<Friendship> friendships = friendshipRepository.getFriends(userId);
        for (Friendship friendship : friendships) {
            Optional<User> userOptional;
            if (!friendship.getUserId().equals(userId)) {
                userOptional = userRepository.findById(friendship.getUserId());
            } else {
                userOptional = userRepository.findById(friendship.getFriendId());
            }
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                allFriends.add(new FriendDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername()));
            }
        }
        return allFriends;
    }

    @Override
    public List<FriendDto> getPendingRequest(Long userId) throws SQLException {
        List<FriendDto> allRequest = new ArrayList<>();
        List<Friendship> friendships = friendshipRepository.getPendingRequests(userId);
        for (Friendship friendship : friendships) {
            Optional<User> userOptional = userRepository.findById(friendship.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                allRequest.add(new FriendDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername()));
            }
        }
        return allRequest;
    }
}
