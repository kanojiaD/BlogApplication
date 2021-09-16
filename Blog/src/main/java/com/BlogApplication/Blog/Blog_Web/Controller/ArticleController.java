package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.DTO.ArticleRequestDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.ArticleResponseDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<List<ArticleResponseDetails>>(this.articleService.allArticle(),
                                                                HttpStatus.FOUND);
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
        return new ResponseEntity<List<ArticleResponseDetails>>(this.articleService.getArticleByTag(tagname),
                                                                 HttpStatus.FOUND);
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
        return new ResponseEntity<ArticleResponseDetails>(this.articleService.viewArticleBySlug(slug),
                                                            HttpStatus.FOUND);
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
        return new ResponseEntity<ArticleResponseDetails>(articleService.createArticle(tagname, article),
                                                            HttpStatus.CREATED);
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
    @PutMapping("/blog/article/{articleid}/")
    public ResponseEntity<ResponseDto> updateArticle(@PathVariable String articleid,
                                                 @RequestBody Article article)
    {
        return new ResponseEntity<>(this.articleService.updateArticle(Long.parseLong(articleid), article),
                                    HttpStatus.CREATED);
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
        return new ResponseEntity<>(this.articleService.deleteArticle(Long.parseLong(articleid)),
                             HttpStatus.GONE);
    }

    /**
     * 7.
     *  This API is use for add a tag in existing article.
     *  Authentication required.
     * @param : ?id=someArticleId&tag=someTagName
     * @return : Article
     */
    @PutMapping("/blog/article/")
    public ResponseEntity<ArticleResponseDetails> addTagInArticle(@RequestParam("id") Long articleid,
                                                                  @RequestParam("tag") String tagname)
    {
       return new ResponseEntity<>(this.articleService.addTagInArticle(articleid, tagname),
                             HttpStatus.CREATED);
    }

    /**
     * remove tag from article
     * @param ?removetag=someTagName
     */
    @PutMapping("/blog/article/{articleId}/tag")
    public ResponseEntity<ResponseDto> removeArticlesTag(@PathVariable(value = "articleId") Long id,
                                    @RequestParam(value = "removetag") String tagname)
    {
        return new ResponseEntity<>(this.articleService.removeArticlesTag(id, tagname),HttpStatus.GONE);
    }


    /**
     * 8.
     * This API is use for pagination.
     * Authentication required.
     * @return List of article.
     */
    @GetMapping("/blog/article/")
    public ResponseEntity<List<ArticleResponseDetails>> getArticleByOrder()
    {
        return new ResponseEntity<List<ArticleResponseDetails>>(this.articleService.getArticleByOrder(), HttpStatus.FOUND);
    }

    /**
     * API is use for search article by tag and editor and article name.
     * Authentication not required.
     * @param ?tag=someTag&editor=someEditor&article=someArticleName
     * @return List of Article
     */
    @GetMapping("/blog/searchArticle/")
    public ResponseEntity<List<ArticleResponseDetails>> searchArticle(@RequestParam(value = "tag", required = false) String tagname,
                                                       @RequestParam(value = "editor", required = false) String username,
                                                       @RequestParam(value = "article", required = false) String title)

    {
        return
        new ResponseEntity<>(this.articleService.searchArticle(tagname, username, title), HttpStatus.FOUND);
    }

}
