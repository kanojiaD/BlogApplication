package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public List<Article> allArticle() {
        return articleRepository.findAll();
    }

    public Article createArticle(Article article) {
        article.setSlag(article.getTitle().replace(' ','_')+article.getArticleId());
        article.setPublishedDate(new Date());
        article.setUpdatedDate(new Date());
        //articleRepository.save(article);
        return article;
    }
}
