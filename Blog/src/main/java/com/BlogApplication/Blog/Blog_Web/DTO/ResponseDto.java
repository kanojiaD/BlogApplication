package com.BlogApplication.Blog.Blog_Web.DTO;

import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Blogger;
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
    public ResponseDto(Blogger blogger)
    {
        status ="Registration successful!";
        message = "Your login Id is: "+blogger.getEmail()+" and Password is: "+blogger.getPassword();
    }
    public ResponseDto(Article article)
    {
        status ="Done";
        message = article.getTitle()+" has been removed successfully!!";
    }

}
