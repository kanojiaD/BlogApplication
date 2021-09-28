package com.BlogApplication.Blog.Blog_Web.Message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class ExceptionMessage {
    private String timeStamp;
    private String message;

    public ExceptionMessage(Date timeStamp, String message) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        this.timeStamp = formatter.format(timeStamp);
        this.message = message;
    }
}
