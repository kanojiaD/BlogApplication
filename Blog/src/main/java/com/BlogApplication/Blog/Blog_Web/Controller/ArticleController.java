package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
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
     * 1.
     * This rest API is use for fetch all the article who are written by blogger.
     * Authentication not required.
     * @return : List of All Article.
     */
    @GetMapping("/blog/")
    public ResponseEntity<List<Article>> allArticle()
    {
        return this.articleService.allArticle();
    }

    /**
     * This rest API is use for fetch the article by tag which belongs to the same tag that are given in pathvariable.
     * Authentication not required.
     * @Pathvariable : tagname
     * @return : List of Article
     */
    @GetMapping("/blog/tag/{tagname}/articles/")
    public ResponseEntity<List<Article>> getArticleByTag(@PathVariable String tagname)
    {
        return this.articleService.getArticleByTag(tagname);
    }

    /**
     * This rest API is use for fetch the article who belongs to the same slug as given in @Pathvariable.
     * Authentication not required.
     * @Pathvariable : slug
     * @return : Article
     */
    @GetMapping("/blog/article/{slug}/")
    public ResponseEntity<Article> viewArticleBySlug(@PathVariable String slug)
    {
        return this.articleService.viewArticleBySlug(slug);
    }

    /**
     * This API is use of create an article.
     * Authentication required.
     * @RequestBody : Article
     * @return : Article
     */
    @PostMapping("/blog/article/")
    public ResponseEntity<Article> createArticle(@RequestBody Article article)
    {
        return articleService.createArticle(article);
    }

    /**
     * This API is use for update the article of articleid.
     * Only to be update attribute will be given.
     * Authentication required.
     * @Pathvariable : articleid
     * @RequestBody : article
     * @return : Article
     */
    @PutMapping("blog/article/{articleid}/")
    public ResponseEntity<Article> updateArticle(@PathVariable String articleid,
                                                 @RequestBody Article article)
    {
        return this.articleService.updateArticle(Long.parseLong(articleid), article);
    }

    /**
     * This API is use for delete the article
     * Authentication required.
     * @Pathvariable : articleid
     * @return
     */
    @DeleteMapping("blog/article/{articleid}/")
    public void deleteArticle(@PathVariable String articleid)
    {
        this.articleService.deleteArticle(Long.parseLong(articleid));
    }

    /**
     *  This API is use for add a tag in existing article.
     *  Authentication required.
     * @param : ?id=someid&tag=sometag
     * @return : Article
     */
    @PutMapping("blog/article/")
    public ResponseEntity<Article> addTagInArticle(@RequestParam("id") String articleid,
                                @RequestParam("tag") String tagname)
    {
       return this.articleService.addTagInArticle(Long.parseLong(articleid), tagname);
    }



}
