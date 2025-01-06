package org.example.mrilki.repositoris;

import org.example.mrilki.models.Friendship;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends CrudRepository<Friendship> {


    List<Friendship> getFriends(Long userId) throws SQLException;

    List<Friendship> getFriendsLimit(Long userId, int limit) throws SQLException;

    List<Friendship> getPendingRequests(Long userId) throws SQLException;

    Optional<Friendship> getFriendshipByUser(Long user_id, Long friend_id) throws SQLException;
}
