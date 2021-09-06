package com.BlogApplication.Blog.Blog_Web.Message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class ExceptionMessage {
    private Date timeStamp;
    private String message;

    public ExceptionMessage(Date timeStamp, String message) {
        this.timeStamp = timeStamp;
        this.message = message;
    }
}
