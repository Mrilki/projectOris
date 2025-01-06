package org.example.mrilki.servlets;

import org.example.mrilki.models.Image;
import org.example.mrilki.service.ImageService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteImage")
public class DeleteImageServlet extends HttpServlet {

    private ImageService imageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        imageService = (ImageService) config.getServletContext().getAttribute("imageService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long dayId = Long.parseLong(req.getParameter("dayId"));

        try {

            Image existingImage = imageService.getImageByDay(dayId);


            if (existingImage != null) {
                imageService.deleteImage(existingImage.getId());
            }

            resp.sendRedirect(req.getContextPath() + "/editDay/" + dayId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}