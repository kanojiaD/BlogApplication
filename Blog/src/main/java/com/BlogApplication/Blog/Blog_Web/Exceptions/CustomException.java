package com.BlogApplication.Blog.Blog_Web.Exceptions;

public class CustomException extends RuntimeException{

    private static final long serialVersionUID = -1926999756303260243L;

    public CustomException(String message) {
        super(message);
    }
}
