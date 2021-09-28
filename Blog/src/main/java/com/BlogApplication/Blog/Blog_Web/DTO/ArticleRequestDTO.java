package com.BlogApplication.Blog.Blog_Web.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleRequestDTO {
    //private String articleId;
    private String title;
    private String content;
    private List<String> tag;
}
