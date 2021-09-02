package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Blogger;
import com.BlogApplication.Blog.Blog_Web.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

 @Autowired
    UserService userService;

    /**
     * User can see the own details using this API.
     * @Pathvariable userid
     * @return Blogger Details
     */
    @GetMapping("/blog/user/{userid}")
    public ResponseEntity<Blogger> getBlogger(@PathVariable String userid)
    {
        return this.userService.getBlogger(Long.parseLong(userid));
    }

    /**
     * This API is use for User Registration.
     * @RequestBody blogger
     * @return A message that user is able or not to register.
     */
    @PostMapping("/blog/signup/")
    public ResponseEntity<Blogger> userRegistration(@RequestBody Blogger blogger)
    {
        return this.userService.userRegistration(blogger);
    }

    /**
     *
     */
    @GetMapping("/blog/user/{userid}/articles/")
    public ResponseEntity<List<Article>> viewUserAllHisArticle(@PathVariable String userid)
    {
        return this.userService.viewUserAllHisArticle(Long.parseLong(userid));
    }

}
