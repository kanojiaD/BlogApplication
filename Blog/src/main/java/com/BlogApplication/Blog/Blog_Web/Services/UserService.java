package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Blogger;
import com.BlogApplication.Blog.Blog_Web.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<Blogger> userRegistration(Blogger blogger) {
        blogger.setUserid(123L);
        blogger.setUserUUID(new UUID(9223372036854775807L, -9223372036854775808L));
        if(blogger.getRole()== null) blogger.setRole("ROLE_USER");
        this.userRepository.save(blogger);
        return new ResponseEntity<Blogger>(blogger, HttpStatus.CREATED);
    }

    public ResponseEntity<Blogger> getBlogger(Long userid) {
        return new ResponseEntity<Blogger>(userRepository.findById(userid).get(), HttpStatus.OK);
    }

    public ResponseEntity<List<Article>> viewUserAllHisArticle(long userid) {
        Blogger blogger= userRepository.getById(userid);
        return new ResponseEntity<List<Article>>(blogger.getListOfArticle(),
                HttpStatus.FOUND);
    }
}
