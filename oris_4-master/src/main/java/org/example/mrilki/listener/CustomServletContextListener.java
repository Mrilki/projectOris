package org.example.mrilki.listener;

import org.example.mrilki.repositoris.*;
import org.example.mrilki.service.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CustomServletContextListener implements ServletContextListener {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "278145";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/dzoris";
    private static final String DB_DRIVER = "org.postgresql.Driver";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource);
        SignUpService signUpService = new SignUpServiceImpl(userRepository);
        servletContext.setAttribute("signUpService", signUpService);
        SignInServiceImpl signInService = new SignInServiceImpl(userRepository);
        servletContext.setAttribute("signInService", signInService);
        ProfileService profileService = new ProfileServiceImpl(userRepository);
        servletContext.setAttribute("profileService", profileService);

        FriendshipRepository friendshipRepository = new FriendshipRepositoryJdbcImpl(dataSource);
        FriendshipService friendshipService = new FriendshipServiceImpl(friendshipRepository, userRepository);
        servletContext.setAttribute("friendshipService", friendshipService);

        DayRepository dayRepository = new DayRepositoryJdbcImpl(dataSource);
        DayService dayService = new DayServiceImpl(dayRepository);
        servletContext.setAttribute("dayService", dayService);

        ReportRepository reportRepository = new ReportRepositoryJdbcImpl(dataSource);
        ReportService reportService = new ReportServiceImpl(dayRepository, reportRepository);
        servletContext.setAttribute("reportService", reportService);

        CommentRepository commentRepository = new CommentRepositoryJdbcImpl(dataSource);
        CommentService commentService = new CommentServiceImpl(commentRepository);
        servletContext.setAttribute("commentService", commentService);

        ImageRepository imageRepository = new ImageRepositoryJdbcImpl(dataSource);
        ImageService imageService = new ImageServiceImpl(imageRepository);
        servletContext.setAttribute("imageService", imageService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
