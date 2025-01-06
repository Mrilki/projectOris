package org.example.mrilki.servlets;

import org.example.mrilki.dto.ProfileDto;
import org.example.mrilki.service.ProfileService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/editProfile")
public class EditProfileServlet extends HttpServlet {

    ProfileService profileService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        try {
            ProfileDto profile = profileService.getProfile(username);
            req.setAttribute("profile", profile);
            req.getRequestDispatcher("/jsp/editProfile.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        int age = Integer.parseInt(req.getParameter("age"));
        String username = (String) req.getSession().getAttribute("username");
        ProfileDto profileDto = ProfileDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .username(username)
                .build();
        try {
            profileService.updateProfile(profileDto);
            resp.sendRedirect(req.getContextPath() + "/profile/" + username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        profileService = (ProfileService) config.getServletContext().getAttribute("profileService");
    }

}
