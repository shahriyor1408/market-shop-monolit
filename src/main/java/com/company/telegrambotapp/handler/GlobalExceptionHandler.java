package com.company.telegrambotapp.handler;

import com.company.telegrambotapp.exceptions.GenericNotFoundException;
import com.company.telegrambotapp.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        String requestURI = request.getRequestURI();
        return new ResponseEntity<>(ApiErrorResponse
                .builder()
                .friendlyMessage("Invalid Params Provided")
                .errorFields(errors)
                .requestPath(requestURI)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> globalException(Exception ex, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return new ResponseEntity<>(ApiErrorResponse
                .builder()
                .friendlyMessage(ex.getLocalizedMessage())
                .developerMessage(ex.getMessage())
                .requestPath(requestURI)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenericNotFoundException.class)
    protected ResponseEntity<Object> notFoundException(GenericNotFoundException ex, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return new ResponseEntity<>(ApiErrorResponse
                .builder()
                .friendlyMessage(ex.getMessage())
                .requestPath(requestURI)
                .build(), HttpStatus.BAD_REQUEST);
    }
}
