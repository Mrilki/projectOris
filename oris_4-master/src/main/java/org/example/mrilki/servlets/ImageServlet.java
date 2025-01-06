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

@WebServlet("/uploaded/files")
public class ImageServlet extends HttpServlet {

    private ImageService imageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.imageService = (ImageService) config.getServletContext().getAttribute("imageService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String fileId = req.getParameter("id");
        // получили информацию о загруженном файле
        Image image = imageService.getFileInfo(Long.parseLong(fileId));
        // в ответ указали какого-типа данные уйдут клиенту
        response.setContentType(image.getType());
        // в ответ указали какой размер данных
        response.setContentLength(image.getSize().intValue());
        // в ответ указали оригинальнгое название файла
        response.setHeader("Content-Disposition", "filename=\"" + image.getOriginalFileName() + "\"");
        // записываем данные самого файла в ответ
        imageService.writeFileFromStorage(Long.parseLong(fileId), response.getOutputStream());
        response.flushBuffer();
    }
}