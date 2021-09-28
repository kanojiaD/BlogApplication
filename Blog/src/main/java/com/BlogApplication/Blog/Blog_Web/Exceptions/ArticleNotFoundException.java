package com.BlogApplication.Blog.Blog_Web.Exceptions;

public class ArticleNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 144045702511869249L;

    public ArticleNotFoundException() {
        super("Article Not Found!!");
    }

    public ArticleNotFoundException(String message) {
        super(message);
    }
}
