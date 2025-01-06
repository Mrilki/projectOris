package org.example.mrilki.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        String requestURI = request.getRequestURI();
        if (requestURI.endsWith(".css") || requestURI.endsWith(".js") || requestURI.endsWith(".jpg") || requestURI.endsWith(".png")) {
            filterChain.doFilter(request, response);
        } else {
            Boolean isAuthenticated = false;
            Boolean sessionExists = session != null;
            Boolean isLoginPage = request.getRequestURI().equals("/signIn");
            Boolean isSignUpPage = request.getRequestURI().equals("/signUp");
            String username = "";
            if (sessionExists) {
                isAuthenticated = (Boolean) session.getAttribute("authentication");
                username = (String) session.getAttribute("username");
                if (isAuthenticated == null) {
                    isAuthenticated = false;
                }
            }
            if ((isAuthenticated && !isLoginPage && !isSignUpPage) || (!isAuthenticated && isLoginPage) || (!isAuthenticated && isSignUpPage)) {

                filterChain.doFilter(request, response);
            } else if (isAuthenticated) {

                response.sendRedirect("/profile/" + username);
            } else {
                response.sendRedirect("/signIn");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
