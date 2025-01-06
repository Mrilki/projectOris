package org.example.mrilki.repositoris;

import org.example.mrilki.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {

    Optional<User> findByUsername(String username) throws SQLException;

}
