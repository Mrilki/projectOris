package org.example.mrilki.service;

import org.example.mrilki.dto.CommentDto;
import org.example.mrilki.models.Comment;
import org.example.mrilki.repositoris.CommentRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentDto> getAllCommentsByDay(Long id) throws SQLException {

        List<CommentDto> commentDtos = new ArrayList<>();
        List<Comment> comments = commentRepository.getCommentsByDay(id);
        for (Comment comment : comments) {
            commentDtos.add(CommentDto.builder()
                    .id(comment.getId())
                    .text(comment.getText())
                    .day_id(comment.getDay_id())
                    .user_id(comment.getAuthor_id())
                    .build());
        }

        return commentDtos;
    }

    @Override
    public void deleteComment(Long id) throws SQLException {
        commentRepository.remove(id);
    }

    @Override
    public void createComment(CommentDto comment) throws SQLException {
        System.out.println(comment);
        String text = comment.getText();
        Long day_id = comment.getDay_id();
        Long user_id = comment.getUser_id();

        commentRepository.save(Comment.builder()
                .text(text)
                .day_id(day_id)
                .author_id(user_id)
                .build());
    }
}
