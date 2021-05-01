package com.leverx.exception_handling;

import com.leverx.exception_handling.exception.NoSuchEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PageNotFoundData> handleException(Exception exception) {
        log.error("Exception occurred: " + exception);
        System.out.println(exception);
        PageNotFoundData data = new PageNotFoundData();
        data.setInfo("Page not found");

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
