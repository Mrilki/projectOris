package org.example.mrilki.servlets;

import org.example.mrilki.dto.DayDto;
import org.example.mrilki.service.DayService;
import org.example.mrilki.service.ImageService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addDay")
@MultipartConfig
public class AddDayServlet extends HttpServlet {
    DayService dayService;
    ImageService imageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        dayService = (DayService) config.getServletContext().getAttribute("dayService");
        imageService = (ImageService) config.getServletContext().getAttribute("imageService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/addDay.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String date = req.getParameter("date");
        String text = req.getParameter("text");
        Long userId = Long.parseLong(req.getParameter("userId"));

        Part part = req.getPart("file");


        try {
            dayService.createDay(DayDto.builder()
                    .text(text)
                    .date(date)
                    .userId(userId)
                    .build());
            DayDto dayDto = dayService.getDayDtoLast(userId);
            if (part.getSize() > 5) {
                imageService.saveFileToStorage(part.getInputStream(), part.getSubmittedFileName(), part.getContentType(), dayDto.getId(), part.getSize());
            }

            resp.sendRedirect(req.getContextPath() + "/profile/" + req.getSession().getAttribute("username"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
