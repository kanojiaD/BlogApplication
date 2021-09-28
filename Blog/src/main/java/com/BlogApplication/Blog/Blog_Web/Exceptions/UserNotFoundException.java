package com.BlogApplication.Blog.Blog_Web.Exceptions;

public class UserNotFoundException extends RuntimeException{

    private static final long serialVersionUID = -7264785495339606829L;

    public UserNotFoundException() {
        super("Invalid User Email or Password!! ");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
