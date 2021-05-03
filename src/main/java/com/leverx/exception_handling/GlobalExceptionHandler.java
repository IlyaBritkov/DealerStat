package com.leverx.exception_handling;

import com.leverx.exception_handling.exception.GameCreationException;
import com.leverx.exception_handling.exception.JwtAuthenticationException;
import com.leverx.exception_handling.exception.NoSuchEntityException;
import com.leverx.exception_handling.exception.UserSignUpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<EntityIncorrectData> handleException(NoSuchEntityException exception) {
        EntityIncorrectData data = new EntityIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<EntityIncorrectData> handleException(GameCreationException exception) {
        EntityIncorrectData data = new EntityIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<EntityIncorrectData> handleException(UserSignUpException exception) {
        EntityIncorrectData data = new EntityIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseExceptionData> handleException(AccessDeniedException exception) {
        log.trace("Exception: {}", exception.toString());
        ResponseExceptionData data = new ResponseExceptionData();
        data.setInfo("You have not access to do this");

        return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseExceptionData> handleException(JwtAuthenticationException exception) {
        log.trace("Exception: {}", exception.toString());
        ResponseExceptionData data = new ResponseExceptionData();
        data.setInfo("You have to authorize to access");

        return new ResponseEntity<>(data, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseExceptionData> handleException(Exception exception) {
        log.trace("Exception: {}", exception.toString());
        ResponseExceptionData data = new ResponseExceptionData();
        data.setInfo("Page not found");
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
