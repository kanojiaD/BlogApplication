package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.Entity.Blogger;
import com.BlogApplication.Blog.Blog_Web.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

 @Autowired
    UserService userService;

    @GetMapping("/blog/user/{userid}")
    public ResponseEntity<Blogger> getBlogger(@PathVariable String userid)
    {
        return this.userService.getBlogger(Long.parseLong(userid));
    }

    @PostMapping("/blog/signup/")
    public ResponseEntity<Blogger> userRegistration(@RequestBody Blogger blogger)
    {
        return this.userService.userRegistration(blogger);
    }
}
