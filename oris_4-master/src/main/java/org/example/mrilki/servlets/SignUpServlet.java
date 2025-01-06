package org.example.mrilki.servlets;

import org.example.mrilki.dto.SignUpForm;
import org.example.mrilki.service.SignUpService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private SignUpService signUpService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        signUpService = (SignUpService) config.getServletContext().getAttribute("signUpService");

        if (signUpService == null) {
            throw new ServletException("SignUpService не инициализирован.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/signUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String ageStr = req.getParameter("age");
        String password = req.getParameter("password");
        String username = req.getParameter("username");


        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                ageStr == null || ageStr.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                username == null || username.trim().isEmpty()) {
            resp.sendRedirect("/signUp?error=emptyFields");
            return;
        }


        int age;
        try {
            age = Integer.parseInt(ageStr.trim());
            if (age < 1 || age > 120) {
                resp.sendRedirect("/signUp?error=invalidAge");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("/signUp?error=invalidAge");
            return;
        }


        if (password.length() < 6) {
            resp.sendRedirect("/signUp?error=shortPassword");
            return;
        }


        boolean userExists;
        userExists = signUpService.isUsernameExists(username);

        if (userExists) {
            resp.sendRedirect("/signUp?error=userExists");
            return;
        }


        SignUpForm signUpForm = SignUpForm.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .password(password)
                .username(username)
                .build();


        try {
            signUpService.signUp(signUpForm);
            resp.sendRedirect("/signIn?success=registrationComplete");
        } catch (SQLException e) {
            throw new ServletException("Ошибка при регистрации пользователя.", e);
        }
    }
}