package cheque.handover.services.Configration;

import cheque.handover.services.Controller.User;
import cheque.handover.services.Model.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@ControllerAdvice

public class GlobalException implements AuthenticationEntryPoint {
    private Logger logger = LoggerFactory.getLogger(AuthenticationEntryPoint.class);

    CommonResponse commonResponse = new CommonResponse();

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CommonResponse> handleUserNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        logger.error(ex.getMessage());
        commonResponse.setCode("401");
        commonResponse.setMsg(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
    }

    // You can add more exception handlers for different exceptions here
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        logger.error(ex.getMessage());

        commonResponse.setCode("500");
        commonResponse.setMsg(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error(ex.getMessage());

        commonResponse.setCode("500");
        commonResponse.setMsg(ex.getMessage());

        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        System.out.println(authException);
        writer.println("Access Denied !! " + authException.getMessage());

    }
}
