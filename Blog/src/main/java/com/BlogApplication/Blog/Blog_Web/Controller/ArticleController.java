package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.DTO.ArticleRequestDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.ArticleResponseDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @GetMapping("/blog/articles/")
    public ResponseEntity<List<ArticleResponseDetails>> allArticle()
    {
        return this.articleService.allArticle();
    }

    /**
     * 2.
     * This rest API is use for fetch the article by tag which belongs to the same tag that are given in pathvariable.
     * Authentication not required.
     * @Pathvariable : tagname
     * @return : List of Article
     */
    @GetMapping("/blog/tag/{tagname}/articles/")
    public ResponseEntity<List<ArticleResponseDetails>> getArticleByTag(@PathVariable String tagname)
    {
        return this.articleService.getArticleByTag(tagname);
    }

    /**
     * 3.
     * This rest API is use for fetch the article who belongs to the same slug as given in @Pathvariable.
     * Authentication not required.
     * @Pathvariable : slug
     * @return : Article
     */
    @GetMapping("/blog/article/{slug}/")
    public ResponseEntity<ArticleResponseDetails> viewArticleBySlug(@PathVariable String slug)
    {
        return this.articleService.viewArticleBySlug(slug);
    }

    /**
     * 4.
     * This API is use for create an article.
     * Authentication required.
     * @RequestBody : Article
     * @param ?tag=tagname
     * @return : Article
     */
    @PostMapping("/blog/article/")
    public ResponseEntity<ArticleResponseDetails> createArticle(@Valid @RequestBody ArticleRequestDetails article,
                                                                @RequestParam(value = "tag", required = false) String tagname)
    {
        return articleService.createArticle(tagname, article);
    }


    /**
     * 5.
     * This API is use for update the article of articleid.
     * Only to be update attribute will be given.
     * Authentication required.
     * @Pathvariable : articleid
     * @RequestBody : article
     * @return : Article
     */
    @PutMapping("blog/article/{articleid}/")
    public ResponseEntity<ResponseDto> updateArticle(@PathVariable String articleid,
                                                 @RequestBody Article article)
    {
        return this.articleService.updateArticle(Long.parseLong(articleid), article);
    }

    /**
     * 6.
     * This API is use for delete the article
     * Authentication required.
     * @Pathvariable : articleid
     * @return
     */
    @DeleteMapping("blog/article/{articleid}/")
    public ResponseEntity<ResponseDto> deleteArticle(@PathVariable String articleid)
    {
        return this.articleService.deleteArticle(Long.parseLong(articleid));
    }

    /**
     * 7.
     *  This API is use for add a tag in existing article.
     *  Authentication required.
     * @param : ?id=someArticleId&tag=someTagName
     * @return : Article
     */
    @PutMapping("blog/article/")
    public ResponseEntity<ArticleResponseDetails> addTagInArticle(@RequestParam("id") String articleid,
                                                                  @RequestParam("tag") String tagname)
    {
       return this.articleService.addTagInArticle(Long.parseLong(articleid), tagname);
    }

    /**
     * 8.
     * This API is use for pagination.
     * Authentication required.
     * @return List of article.
     */
    @GetMapping("/blog/articleByOrder/")
    public ResponseEntity<List<ArticleResponseDetails>> getArticleByOrder()
    {
        return this.articleService.getArticleByOrder();
    }

    /**
     * API is use for search article by tag and editor and article name.
     * Authentication not required.
     * @param ?tag=someTag&editor=someEditor&article=someArticleName
     * @return List of Article
     */
    @GetMapping("/blog/searchArticle/")
    public ResponseEntity<List<Article>> searchArticle(@RequestParam(value = "tag", required = false) String tagname,
                                                       @RequestParam(value = "editor", required = false) String username,
                                                       @RequestParam(value = "article", required = false) String title)

    {
        return this.articleService.searchArticle(tagname, username, title);
    }

}
