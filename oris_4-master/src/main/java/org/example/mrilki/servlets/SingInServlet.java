package org.example.mrilki.servlets;

import org.example.mrilki.dto.SignInForm;
import org.example.mrilki.dto.UserDto;
import org.example.mrilki.service.SignInServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/signIn")
public class SingInServlet extends HttpServlet {
    private SignInServiceImpl signInService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        signInService = (SignInServiceImpl) config.getServletContext().getAttribute("signInService");

        if (signInService == null) {
            throw new ServletException("SignInService не инициализирован.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");


        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            resp.sendRedirect("/signIn?error=emptyFields");
            return;
        }

        SignInForm signInForm = SignInForm.builder()
                .username(username)
                .password(password)
                .build();
        try {
            UserDto userDto = signInService.singIn(signInForm);
            if (userDto != null) {
                HttpSession session = req.getSession(true);
                session.setAttribute("authentication", true);
                session.setAttribute("id", userDto.getId());
                session.setAttribute("username", userDto.getUsername());
                session.setAttribute("role", userDto.getRole());
                resp.sendRedirect("/profile/" + userDto.getUsername());
            } else {
                resp.sendRedirect("/signIn?error=invalidCredentials");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during sign in.", e);
        }
    }
}
