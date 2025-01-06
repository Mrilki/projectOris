package org.example.mrilki.servlets;

import org.example.mrilki.dto.FriendDto;
import org.example.mrilki.dto.FriendshipDto;
import org.example.mrilki.dto.ProfileDto;
import org.example.mrilki.service.FriendshipService;
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

@WebServlet("/friends")
public class FriendshipServlet extends HttpServlet {

    FriendshipService friendshipService;
    ProfileService profileService;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String action = req.getParameter("action");
        String requestId = req.getParameter("requestId");


        if (username == null && action == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Long curId = (Long) req.getSession().getAttribute("id");

        if (username != null) {
            try {
                ProfileDto usernameId = profileService.getProfile(username);
                if (usernameId != null) {
                    friendshipService.sendFriendRequest(FriendshipDto.builder()
                            .userId(curId)
                            .friendId(usernameId.getId())
                            .build());
                }
                resp.sendRedirect(req.getContextPath() + "/friends?id=" + curId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else if (action.equals("accept")) {
            try {
                FriendshipDto friendshipDto = FriendshipDto.builder()
                        .userId(curId)
                        .friendId(Long.valueOf(requestId))
                        .build();
                friendshipService.acceptFriendRequest(friendshipDto);
                resp.sendRedirect(req.getContextPath() + "/friends?id=" + curId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (action.equals("reject")) {
            try {
                FriendshipDto friendshipDto = FriendshipDto.builder()
                        .userId(curId)
                        .friendId(Long.valueOf(requestId))
                        .build();
                friendshipService.rejectFriendRequest(friendshipDto);
                resp.sendRedirect(req.getContextPath() + "/friends?id=" + curId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        if (userId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                List<FriendDto> friends = friendshipService.getFriends(Long.valueOf(userId));
                List<FriendDto> friendRequests = friendshipService.getPendingRequest(Long.valueOf(userId));
                Long curId = (Long) req.getSession().getAttribute("id");
                Boolean isOwner = curId.equals(Long.valueOf(userId));
                req.setAttribute("friends", friends);
                req.setAttribute("friendRequests", friendRequests);
                req.setAttribute("isOwner", isOwner);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            req.getRequestDispatcher("/jsp/friends.jsp").forward(req, resp);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        friendshipService = (FriendshipService) config.getServletContext().getAttribute("friendshipService");
        profileService = (ProfileService) config.getServletContext().getAttribute("profileService");
    }
}
