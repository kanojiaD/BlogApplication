package com.BlogApplication.Blog.Blog_Web.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class BloggerDetails {
    private String username;
    private String email;
    private String contact;
    Set<ArticleDetails> listOfArticle;
}
