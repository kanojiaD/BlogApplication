package com.BlogApplication.Blog.Blog_Web.Message;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomMessage {
    private String message;
    public CustomMessage(String message) {
        this.message = message;
    }
}
