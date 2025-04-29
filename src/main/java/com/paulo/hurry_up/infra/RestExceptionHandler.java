package com.paulo.hurry_up.infra;

import com.paulo.hurry_up.exceptions.EventNotFoundException;
import com.paulo.hurry_up.exceptions.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EventNotFoundException.class)
    private ResponseEntity<ResponseException> eventNotFoundHandler(EventNotFoundException exception) {
        ResponseException response = new ResponseException(exception.getMessage(), HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
