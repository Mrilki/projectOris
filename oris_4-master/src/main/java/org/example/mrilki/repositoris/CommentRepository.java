package org.example.mrilki.repositoris;

import org.example.mrilki.models.Comment;

import java.sql.SQLException;
import java.util.List;


public interface CommentRepository extends CrudRepository<Comment> {
    public List<Comment> getCommentsByDay(Long id) throws SQLException;


}
