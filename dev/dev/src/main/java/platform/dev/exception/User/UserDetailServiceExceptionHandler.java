package platform.dev.exception.User;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserDetailServiceExceptionHandler {

    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/exception/userdetails");
    }
}