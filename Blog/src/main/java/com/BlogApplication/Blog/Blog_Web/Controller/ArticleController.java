package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     * This API is use for extract and watch all the article who are written by blogger without login.
     * @return list of all article.
     */
    @GetMapping("/blog/")
    public ResponseEntity<List<Article>> allArticle()
    {
        return articleService.allArticle();
    }

    /**
     *
     */
    @GetMapping("/blog/tag/{tagname}/articles/")
    public ResponseEntity<List<Article>> getArticleByTag(@PathVariable String tagname)
    {
        return this.articleService.getArticleByTag(tagname);
    }

    /**
     * This API is use of create an article.
     * @RequestBody article
     * @return
     */
    @PostMapping(value = "/blog/article/")
    public ResponseEntity<Article> createArticle(@RequestBody Article article)
    {
        return articleService.createArticle(article);
    }

    /**
     * deleteArticle:
     */
    @DeleteMapping("blog/article/{articleid}/")
    public void deleteArticle(@PathVariable String articleid)
    {
        this.articleService.deleteArticle(Long.parseLong(articleid));
    }

}
