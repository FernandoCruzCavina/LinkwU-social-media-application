package dev.fernando.user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageException> handleUserNotFoundException(UserNotFoundException ex) {
        MessageException messageException = new MessageException(ex.getMessage(), HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageException);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<MessageException> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        MessageException messageException = new MessageException(ex.getMessage(), HttpStatus.CONFLICT);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageException);
    }
}