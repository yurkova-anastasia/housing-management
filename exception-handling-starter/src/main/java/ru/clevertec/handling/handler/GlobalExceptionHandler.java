package ru.clevertec.handling.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.handling.exception.MappingException;
import ru.clevertec.handling.exception.ServiceException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ServiceException.class, MappingException.class})
    public ResponseEntity<ExceptionMessage> handleServiceAndMappingException(HttpServletRequest request,
                                                                             ServiceException ex) {

        ExceptionMessage exceptionMessage = new ExceptionMessage(LocalDateTime.now(),
                ex.getHttpStatus().value(),
                ex.getHttpStatus().getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(exceptionMessage, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> handleValidationException(
            HttpServletRequest request,
            Exception ex
    ) {
        ExceptionMessage response = new ExceptionMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "Arguments are not valid",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handleOtherExceptions(
            HttpServletRequest request,
            Exception ex
    ) {
        ExceptionMessage response = new ExceptionMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
