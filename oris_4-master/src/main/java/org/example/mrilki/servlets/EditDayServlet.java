package org.example.mrilki.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mrilki.dto.DayDto;
import org.example.mrilki.models.Image;
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
import java.util.Map;
import java.util.Optional;

@WebServlet("/editDay/*")
@MultipartConfig
public class EditDayServlet extends HttpServlet {

    private DayService dayService;
    private ImageService imageService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        dayService = (DayService) config.getServletContext().getAttribute("dayService");
        imageService = (ImageService) config.getServletContext().getAttribute("imageService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("update".equals(action)) {
            updateDay(req, resp);
        } else if ("deleteImage".equals(action)) {
            deleteImage(req, resp);
        } else if ("uploadImage".equals(action)) {
            uploadImage(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // Например, "/123"
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "dayId parameter is missing");
            return;
        }

        Long dayId;
        try {
            dayId = Long.parseLong(pathInfo.substring(1)); // Извлекаем "123" из "/123"
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid dayId parameter");
            return;
        }

        try {
            Optional<DayDto> day = dayService.getDay(dayId);
            if (day.isPresent()) {
                DayDto dayDto = day.get();
                req.setAttribute("day", dayDto);
                Image image = imageService.getImageByDay(dayDto.getId());
                if (image != null) {
                    req.setAttribute("image", image);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Day not found");
                return;
            }

            req.getRequestDispatcher("/jsp/editDay.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Database error while fetching day details", e);
        }
    }

    private void updateDay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String dayIdStr = req.getParameter("dayId");
        String date = req.getParameter("date");
        String text = req.getParameter("text");

        if (dayIdStr == null || date == null || text == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters for update");
            return;
        }

        Long dayId;
        try {
            dayId = Long.parseLong(dayIdStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid dayId");
            return;
        }

        try {
            dayService.updateDay(DayDto.builder()
                    .id(dayId)
                    .text(text)
                    .date(date)
                    .build());
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(Map.of("success", true)));
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при обновлении дня");
        }
    }

    private void deleteImage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String dayIdStr = req.getParameter("dayId");

        if (dayIdStr == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "dayId parameter is missing");
            return;
        }

        Long dayId;
        try {
            dayId = Long.parseLong(dayIdStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid dayId");
            return;
        }

        try {
            Image existingImage = imageService.getImageByDay(dayId);
            if (existingImage != null) {
                imageService.deleteImage(existingImage.getId());
            }
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(Map.of("success", true)));
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при удалении изображения");
        }
    }

    private void uploadImage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String dayIdStr = req.getParameter("dayId");
        Part imagePart = req.getPart("image");

        if (dayIdStr == null || imagePart == null || imagePart.getSize() == 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters for image upload");
            return;
        }

        Long dayId;
        try {
            dayId = Long.parseLong(dayIdStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid dayId");
            return;
        }

        try {
            Image existingImage = imageService.getImageByDay(dayId);
            if (existingImage != null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Изображение уже существует");
                return;
            }


            imageService.saveFileToStorage(
                    imagePart.getInputStream(),
                    imagePart.getSubmittedFileName(),
                    imagePart.getContentType(),
                    dayId,
                    imagePart.getSize()
            );
            Image savedImage = imageService.getImageByParam(imagePart.getSubmittedFileName(), dayId, imagePart.getSize());
            System.out.println(savedImage);
            System.out.println(savedImage.getId());


            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(Map.of("success", true, "imageId", savedImage.getId())));
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при загрузке изображения");
        }
    }
}