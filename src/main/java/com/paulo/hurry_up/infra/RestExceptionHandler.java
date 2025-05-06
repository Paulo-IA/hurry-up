package com.paulo.hurry_up.infra;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.paulo.hurry_up.exceptions.InvalidEventFileExtensionException;
import com.paulo.hurry_up.exceptions.EventNotFoundException;
import com.paulo.hurry_up.exceptions.Message;
import com.paulo.hurry_up.exceptions.ResponseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EventNotFoundException.class)
    private ResponseEntity<ResponseException> eventNotFoundHandler(EventNotFoundException exception) {
        ArrayList<Message> errors = new ArrayList<Message>();
        errors.add(new Message("event", exception.getMessage()));

        ResponseException response = new ResponseException(errors, HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidEventFileExtensionException.class)
    private ResponseEntity<ResponseException> invalidEventFileExtensionHandler(InvalidEventFileExtensionException exception) {
        ArrayList<Message> errors = new ArrayList<Message>();
        errors.add(new Message("event", exception.getMessage()));

        ResponseException response = new ResponseException(errors, HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ArrayList<Message> errors = new ArrayList<Message>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    Message message = new Message();

                    message.setFieldName(error.getField());
                    message.setMessage(error.getDefaultMessage());

                    errors.add(message);
                });

        ResponseException response = new ResponseException(errors, HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ArrayList<Message> errors = new ArrayList<>();

        if (ex.getCause() instanceof InvalidFormatException ife) {

            String fieldName = "json";
            if (!ife.getPath().isEmpty()) {
                fieldName = ife.getPath().getLast().getFieldName();
            }

            errors.add(new Message(fieldName, "Formato inválido para este campo"));
        } else {
            errors.add(new Message("json", "Erro na estrutura do JSON"));
        }

        ResponseException response = new ResponseException(
                errors,
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseException> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        ArrayList<Message> errors = new ArrayList<>();

        String paramName = ex.getName();
        String valorRecebido = ex.getValue() != null ? ex.getValue().toString() : "null";
        String tipoEsperado = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido";

        errors.add(new Message(
                paramName,
                String.format("O valor '%s' não é um %s válido", valorRecebido, tipoEsperado)
        ));

        ResponseException response = new ResponseException(errors, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseException> handleGlobalException(Exception ex) {
        ArrayList<Message> errors = new ArrayList<>();

        errors.add(new Message("erro", "Ocorreu um erro interno no servidor"));

        ex.printStackTrace();

        ResponseException response = new ResponseException(errors, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
