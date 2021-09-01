package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Security.Helper.JwtUtil;
import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Blogger;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.Repository.ArticleRepository;
import com.BlogApplication.Blog.Blog_Web.Repository.TagRepository;
import com.BlogApplication.Blog.Blog_Web.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;


    public ResponseEntity<List<Article>> allArticle() {
        return new ResponseEntity<List<Article>>(articleRepository.findAll(), HttpStatus.FOUND);
    }

    @Transactional
    public ResponseEntity<Article> createArticle(Article article) {
        article.setSlug(article.getTitle().replace(' ', '_')+'_'+article.getArticleId());
        article.setPublishedDate(new Date());
        article.setUpdatedDate(new Date());

        Blogger blogger= userRepository.findById(3L).get();
        blogger.addArticleInBloggerList(article);


        article.setBlogger(blogger);

        this.articleRepository.save(article);





        this.userRepository.save(blogger);

        return new ResponseEntity<Article>(article, HttpStatus.CREATED);
    }
}
