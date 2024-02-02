package ru.clevertec.handling.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionMessage {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
