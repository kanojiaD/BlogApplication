package com.BlogApplication.Blog.Blog_Web.Message;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BlogMessage {
    private Date timeStamp= new Date();
    private String message;

    public BlogMessage(String message) {
        this.message = message;
    }
}
