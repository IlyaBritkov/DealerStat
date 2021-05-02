package com.leverx.exception_handling;

import com.leverx.exception_handling.exception.NoSuchEntityException;
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
    public ResponseEntity<ResponseExceptionData> handleException(AccessDeniedException exception) {
        log.error("Exception occurred: " + exception);
        ResponseExceptionData data = new ResponseExceptionData();
        data.setInfo("You have not access to this");

        return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseExceptionData> handleException(Exception exception) {
        log.error("Exception occurred: " + exception);
        ResponseExceptionData data = new ResponseExceptionData();
        data.setInfo("Page not found");

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
