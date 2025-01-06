package org.example.mrilki.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mrilki.dto.CommentDto;
import org.example.mrilki.dto.ProfileDto;
import org.example.mrilki.models.User;
import org.example.mrilki.service.CommentService;
import org.example.mrilki.service.ProfileService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {
    private CommentService commentService;
    private ProfileService profileService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        commentService = (CommentService) config.getServletContext().getAttribute("commentService");
        profileService = (ProfileService) config.getServletContext().getAttribute("profileService");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("getComments".equals(action)) {
            Long dayId = Long.parseLong(req.getParameter("dayId"));

            try {
                List<CommentDto> comments = commentService.getAllCommentsByDay(dayId);
                for (CommentDto comment : comments) {
                    ProfileDto author = profileService.getProfileById(comment.getUser_id());
                    comment.setAuthor(author.getUsername());

                }
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(comments));
            } catch (SQLException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при загрузке комментариев.");
            }
        } else if ("addComment".equals(action)) {
            Long dayId = Long.parseLong(req.getParameter("dayId"));
            String commentText = req.getParameter("commentText");
            Long authorId = Long.parseLong(req.getParameter("authorId"));

            try {
                CommentDto newComment = CommentDto.builder()
                        .text(commentText)
                        .day_id(dayId)
                        .user_id(authorId)
                        .build();
                commentService.createComment(newComment);
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(Map.of("success", true)));
            } catch (SQLException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при добавлении комментария.");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action");
        }
    }

}
