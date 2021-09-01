package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/blog/")
    public ResponseEntity<List<Article>> allArticle()
    {
        return articleService.allArticle();
    }

    @PostMapping(value = "/blog/article/")
    public ResponseEntity<Article> createArticle(@RequestBody Article article)
    {
        return articleService.createArticle(article);
    }
}
