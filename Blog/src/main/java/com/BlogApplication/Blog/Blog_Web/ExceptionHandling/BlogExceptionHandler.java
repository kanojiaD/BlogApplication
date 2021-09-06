package com.BlogApplication.Blog.Blog_Web.ExceptionHandling;

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
    public ResponseEntity<ExceptionMessage> allExceptionHandler(Exception e, WebRequest rquest)
    {
        String errorMessageDescription= e.getLocalizedMessage();
        if(errorMessageDescription==null) errorMessageDescription=e.toString();

        return new ResponseEntity<ExceptionMessage>(new ExceptionMessage(new Date(),
                                                        e.getLocalizedMessage()),
                                                        new HttpHeaders(),
                                                        HttpStatus.BAD_REQUEST);
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
