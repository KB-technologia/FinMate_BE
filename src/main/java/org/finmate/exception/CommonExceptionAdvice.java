package org.finmate.exception;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
@Log4j2
public class CommonExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleCommonException(Exception ex, HttpServletRequest request) {

        ex.printStackTrace();
        log.error("Exception ......." + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .error("Internal Server Error")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }


    @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class})
    public ResponseEntity<ErrorResponse> handle404(HttpServletRequest request, NoHandlerFoundException ex) {

        log.error("Exception ......." + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .error("Not Found")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        log.error("Type Mismatch", ex);
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .error("Bad Request")
                        .message("Invalid parameter: " + ex.getName())
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(OpenAiApiException.class)
    public ResponseEntity<?> handleOpenAiException(HttpServletRequest request,OpenAiApiException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(
                        ErrorResponse.builder()
                                .error("SERVICE_UNAVAILABLE")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

}