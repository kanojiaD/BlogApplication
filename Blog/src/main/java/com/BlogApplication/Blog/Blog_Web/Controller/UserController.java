package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.Entity.Blogger;
import com.BlogApplication.Blog.Blog_Web.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

 @Autowired
    UserService userService;

    @PostMapping("/blog/signup/")
    public ResponseEntity<Blogger> userRegistration(@RequestBody Blogger blogger)
    {
        return this.userService.userRegistration(blogger);
    }
}
