package org.example.mrilki.service;

import org.example.mrilki.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

public interface CommentService {
    List<CommentDto> getAllCommentsByDay(Long id) throws SQLException;

    void deleteComment(Long id) throws SQLException;

    void createComment(CommentDto comment) throws SQLException;
}
