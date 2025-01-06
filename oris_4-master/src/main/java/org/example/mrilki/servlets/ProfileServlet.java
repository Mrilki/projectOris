package org.example.mrilki.servlets;

import org.example.mrilki.dto.DayDto;
import org.example.mrilki.dto.DayWithImage;
import org.example.mrilki.dto.ProfileDto;

import org.example.mrilki.service.DayService;
import org.example.mrilki.service.ImageService;
import org.example.mrilki.service.ProfileService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/profile/*")
public class ProfileServlet extends HttpServlet {

    ProfileService profileService;
    DayService dayService;
    ImageService imageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        profileService = (ProfileService) config.getServletContext().getAttribute("profileService");
        dayService = (DayService) config.getServletContext().getAttribute("dayService");
        imageService = (ImageService) config.getServletContext().getAttribute("imageService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String username = pathInfo.substring(1);
        try {
            ProfileDto profile = profileService.getProfile(username);
            if (profile != null) {
                String curUsername = (String) request.getSession().getAttribute("username");
                Boolean isOwner = username.equals(curUsername);
                request.setAttribute("isOwner", isOwner);
                request.setAttribute("profile", profile);

                List<DayDto> days = dayService.getProfileDay(profile.getId());
                List<DayWithImage> dayWithImage = new ArrayList<>();
                for (DayDto day : days) {
                    dayWithImage.add(new DayWithImage(day, imageService.getImageByDay(day.getId())));
                }
                request.setAttribute("days", dayWithImage);


                request.getRequestDispatcher("/jsp/profile.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
