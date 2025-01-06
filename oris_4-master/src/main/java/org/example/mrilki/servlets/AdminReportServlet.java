package org.example.mrilki.servlets;

import org.example.mrilki.dto.ReportDto;
import org.example.mrilki.dto.ReportWithImage;
import org.example.mrilki.models.Image;
import org.example.mrilki.service.ImageService;
import org.example.mrilki.service.ReportService;
import org.example.mrilki.service.ReportServiceImpl;

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

@WebServlet("/adminReport")
public class AdminReportServlet extends HttpServlet {
    ReportService reportService;
    ImageService imageService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<ReportDto> reports = reportService.getReports();
            List<ReportWithImage> reportWithImages = new ArrayList<>();
            for (ReportDto report : reports) {
                reportWithImages.add(new ReportWithImage(report, imageService.getImageByDay(report.getDayId())));
            }
            req.setAttribute("reports", reportWithImages);
            req.getRequestDispatcher("/jsp/adminReport.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Long reportId = Long.parseLong(req.getParameter("reportId"));
        try {
            if (action.equals("accept")) {
                reportService.acceptedReport(reportId);
            } else if (action.equals("reject")) {
                reportService.rejectedReport(reportId);
            }
            resp.sendRedirect(req.getContextPath() + "/adminReport");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        reportService = (ReportService) config.getServletContext().getAttribute("reportService");
        imageService = (ImageService) config.getServletContext().getAttribute("imageService");
    }
}
