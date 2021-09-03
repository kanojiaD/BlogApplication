package com.BlogApplication.Blog.Blog_Web.Services;

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
    public ResponseEntity<Article> createArticle(long userid, long tagId, Article article) {
        article.setSlug(article.getTitle().replace(' ', '_')+'_'+article.getArticleId());
        article.setPublishedDate(new Date());
        article.setUpdatedDate(new Date());

        Blogger blogger= userRepository.findById(userid).get();
        blogger.addArticleInBloggerList(article);

        article.setBlogger(blogger);

        Tag tag=tagRepository.findById(tagId).get();
        tag.addArticleInTag(article);

        article.addTagInArticle(tag);
        this.articleRepository.save(article);
        this.tagRepository.save(tag);
        this.userRepository.save(blogger);

        return new ResponseEntity<Article>(article, HttpStatus.CREATED);
    }
    

    public void deleteArticle(long articleid) {
        this.articleRepository.delete(articleRepository.findById(articleid).get());
    }


    public ResponseEntity<List<Article>> getArticleByTag(String tagname) {
        Tag tag= this.tagRepository.findByTagName(tagname);
        return new ResponseEntity<List<Article>>(tag.getArticleList(),
                                                 HttpStatus.FOUND);
    }

    public ResponseEntity<Article> viewArticleBySlug(String slug) {
        return new ResponseEntity<>(this.articleRepository.findArticleBySlug(slug),
                                    HttpStatus.FOUND);
    }

    public ResponseEntity<Article> updateArticle(long articleid, Article article) {
        Article oldArticle= articleRepository.findById(articleid).get();
        if(article.getTitle()!=null) {
            oldArticle.setTitle(article.getTitle());
            oldArticle.setSlug(article.getTitle().replace(' ', '_')+'_'+oldArticle.getArticleId());
        }
        if(article.getContent()!=null) oldArticle.setContent(article.getContent());
        oldArticle.setUpdatedDate(new Date());
        this.articleRepository.save(oldArticle);
        return new ResponseEntity<>(oldArticle, HttpStatus.CREATED);
    }

    public ResponseEntity<Article> addTagInArticle(long articleid, String tagname) {
        Article article= articleRepository.findById(articleid).get();
        Tag tag= tagRepository.findByTagName(tagname);
        tag.addArticleInTag(article);
        article.addTagInArticle(tag);
        articleRepository.save(article);
        tagRepository.save(tag);
        return new ResponseEntity<>(article,HttpStatus.CREATED);
    }
}
