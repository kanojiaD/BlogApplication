package com.BlogApplication.Blog.Blog_Web.DTO;

import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@AllArgsConstructor
public class ResponseDto {
    private String status;
    private String message;
    public ResponseDto(Users users)
    {
        status ="Registration successful!";
        message = "Your login Id is: "+ users.getEmail()+" and Password is: "+ users.getPassword();
    }
    public ResponseDto(Article article)
    {
        status ="Done";
        message = article.getTitle()+" has been removed successfully!!";
    }

}
