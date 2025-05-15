package dev.fernando.user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MessageException extends ResponseEntityExceptionHandler {
    private final HttpStatus status;
    private final String message;

    public MessageException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
