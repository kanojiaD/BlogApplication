package com.BlogApplication.Blog.Blog_Web.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class BloggerDetails {
    private String name;
    private String email;
    private String contact;
    Set<ArticleResponseDetails> listOfArticle;
}
