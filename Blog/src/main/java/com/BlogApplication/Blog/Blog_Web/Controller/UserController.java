package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.DTO.ArticleDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.BloggerDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Blogger;
import com.BlogApplication.Blog.Blog_Web.ExceptionHandling.CustomException;
import com.BlogApplication.Blog.Blog_Web.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

 @Autowired
    UserService userService;

    /**
     * 1.
     * This API is use for User Registration.
     * Authentication not required.
     * @RequestBody : blogger
     * @return : A message that registration is successful or not.
     */
    @PostMapping("/blog/signup/")
    public ResponseEntity<ResponseDto> userRegistration(@Valid @RequestBody Blogger blogger)
    {
        try{
            return this.userService.userRegistration(blogger);
        }
        catch (Exception e)
        {
            throw new CustomException("Registration failed!!");
        }
    }

    /**
     * 2.
     * User can see the own details using this API.
     * Authentication required.
     * @Pathvariable : userid
     * @return : Blogger Details
     */
    @GetMapping("/blog/user/{userid}")
    public ResponseEntity<BloggerDetails> getBlogger(@PathVariable String userid)
    {
        return this.userService.getBlogger(Long.parseLong(userid));
    }

    /**
     * 3.
     * Using this API user can see all his article.
     * Authentication required.
     * @Pathvariable : userid
     * @return : List of Article
     */
    @GetMapping("/blog/user/{userid}/articles/")
    public ResponseEntity<List<ArticleDetails>> viewUserAllHisArticle(@PathVariable String userid)
    {
        return this.userService.viewUserAllHisArticle(Long.parseLong(userid));
    }

    /**
     * 4.
     *  This API is use for delete the account of Bloggers.
     *  Authentication required.
     * @return : ResponseDto.
     */
    @DeleteMapping("/blog/user/{userid}/")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable("userid") Long userid)
    {
        return this.userService.deleteUser(userid);
    }

}
