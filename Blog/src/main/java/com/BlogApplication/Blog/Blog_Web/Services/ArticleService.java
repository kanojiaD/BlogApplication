package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public ResponseEntity<List<Article>> allArticle() {
        return new ResponseEntity<List<Article>>(articleRepository.findAll(), HttpStatus.FOUND);
    }

    public ResponseEntity<Article> createArticle(Article article) {
        article.setSlag(article.getTitle().replace(' ','_')+article.getArticleId());
        article.setPublishedDate(new Date());
        article.setUpdatedDate(new Date());
        //articleRepository.save(article);
        return new ResponseEntity<Article>(article, HttpStatus.CREATED);
    }
}
