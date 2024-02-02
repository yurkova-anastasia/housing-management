package ru.clevertec.handling.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MappingException extends RuntimeException {
    private HttpStatus httpStatus;

    public MappingException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}