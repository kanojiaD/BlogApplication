package com.BlogApplication.Blog.Blog_Web.ExceptionHandling;

import com.BlogApplication.Blog.Blog_Web.Message.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class BlogExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionMessage> allExceptionHandler(Exception e)
    {
        return new ResponseEntity<ExceptionMessage>(new ExceptionMessage(new Date(),
                                                        e.getLocalizedMessage(),
                                                        e.getStackTrace()),
                                                        HttpStatus.BAD_REQUEST);
    }
}
