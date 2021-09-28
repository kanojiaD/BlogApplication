package com.BlogApplication.Blog.Blog_Web.ExceptionHandler;

import com.BlogApplication.Blog.Blog_Web.Exceptions.ArticleNotFoundException;
import com.BlogApplication.Blog.Blog_Web.Exceptions.UserNotFoundException;
import com.BlogApplication.Blog.Blog_Web.Message.ExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BlogExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionMessage> allExceptionHandler(Exception ex, WebRequest rquest)
    {
        String errorMessageDescription= ex.getLocalizedMessage();
        if(errorMessageDescription==null) errorMessageDescription=ex.toString();

        return new ResponseEntity<ExceptionMessage>(new ExceptionMessage(new Date(),
                                                        errorMessageDescription),
                                                        new HttpHeaders(),
                                                        HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ExceptionMessage> userNotFoundExceptionHandler(UserNotFoundException ex, WebRequest rquest)
    {
        String errorMessageDescription= ex.getLocalizedMessage();
        if(errorMessageDescription==null) errorMessageDescription=ex.toString();

        return new ResponseEntity<ExceptionMessage>(new ExceptionMessage(new Date(),
                errorMessageDescription),
                new HttpHeaders(),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {ArticleNotFoundException.class})
    public ResponseEntity<ExceptionMessage> articleNotFoundExceptionHandler(ArticleNotFoundException ex, WebRequest rquest)
    {
        String errorMessageDescription= ex.getLocalizedMessage();
        if(errorMessageDescription==null) errorMessageDescription=ex.toString();

        return new ResponseEntity<ExceptionMessage>(new ExceptionMessage(new Date(),
                errorMessageDescription),
                new HttpHeaders(),
                HttpStatus.NO_CONTENT);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String , String> errors= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError)error).getField();
            String message= error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors,HttpStatus.BAD_REQUEST);
    }
}
