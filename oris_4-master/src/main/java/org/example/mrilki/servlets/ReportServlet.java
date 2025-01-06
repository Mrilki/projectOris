package org.example.mrilki.servlets;

import org.example.mrilki.dto.ReportDto;
import org.example.mrilki.service.ReportService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/report/*")
public class ReportServlet extends HttpServlet {
    private ReportService reportService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String dayId = pathInfo.substring(1);
        req.setAttribute("dayId", dayId);
        req.setAttribute("pathInfo", pathInfo);
        req.getRequestDispatcher("/jsp/addReport.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dayId = req.getParameter("dayId");
        String pathInfo = req.getPathInfo();
        if (dayId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Invalid day id");
        }
        String reason = req.getParameter("reason");
        String text = req.getParameter("text");

        if (reason == null || reason.trim().isEmpty() || text == null || text.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/report/" + pathInfo + "?error=emptyFields");
            return;
        }


        try {
            reportService.createReport(ReportDto.builder()
                    .text(text)
                    .reason(reason)
                    .dayId(Long.parseLong(dayId))
                    .build());


            resp.sendRedirect(req.getContextPath() + "/profile/" +
                    req.getSession().getAttribute("username"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        reportService = (ReportService) config.getServletContext().getAttribute("reportService");
    }
}
