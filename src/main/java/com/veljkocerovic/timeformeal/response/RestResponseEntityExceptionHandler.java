package com.veljkocerovic.timeformeal.response;


import com.veljkocerovic.timeformeal.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<ResponseMessage> recipeNotFoundException(RecipeNotFoundException exception,
                                                                   WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage(exception.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseMessage> userNotFoundException(UserNotFoundException exception,
                                                                 WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage(exception.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseMessage> badCredentialsException(BadCredentialsException exception,
                                                                 WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage(exception.getMessage()));
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ResponseMessage> commentNotFoundException(CommentNotFoundException exception,
                                                                   WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage(exception.getMessage()));
    }


    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseMessage> expiredJwtException(ExpiredJwtException exception,
                                                                   WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ResponseMessage(exception.getMessage()));
    }




    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseMessage> userAlreadyExistsException(UserAlreadyExistsException exception,
                                                                      WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ResponseMessage(exception.getMessage()));
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ResponseMessage> tokenNotFoundException(TokenNotFoundException exception,
                                                                  WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage(exception.getMessage()));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ResponseMessage> tokenExpiredException(TokenExpiredException exception,
                                                                 WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseMessage(exception.getMessage()));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ResponseMessage> wrongPasswordException(WrongPasswordException exception,
                                                                  WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ResponseMessage(exception.getMessage()));
    }


    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


}
