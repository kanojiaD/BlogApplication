package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Security.Services.CustomUserDetailsService;
import com.BlogApplication.Blog.Blog_Web.DTO.ArticleResponseDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.BloggerDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Users;
import com.BlogApplication.Blog.Blog_Web.ExceptionHandling.CustomException;
import com.BlogApplication.Blog.Blog_Web.Message.BlogMessage;
import com.BlogApplication.Blog.Blog_Web.Repository.UserRepository;
import com.BlogApplication.Blog.Blog_Web.Utils.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    ServiceUtil util;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public ResponseDto userRegistration(Users users) {
        if(!users.getEmail().toLowerCase().endsWith(".com"))
        {
            return new ResponseDto("Registration failed", "Email must be ends with '.com'");
        }
        users.setUserid(12L);
        users.setUserUUID(UUID.randomUUID().toString());
        ResponseDto responseDto= new ResponseDto(users);
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        if(users.getRole()== null) users.setRole("ROLE_USER");
        this.userRepository.save(users);
        return responseDto;
    }

    public BloggerDetails getUser() {
        Users users = new Users();
        try
        {
             users = userRepository.findUserByEmail(customUserDetailsService.currentLogedInUserName());
        }
        catch(Exception e)
        {
            throw new CustomException("User not found");
        }
        BloggerDetails bloggerDetails= modelMapper.map(users, BloggerDetails.class);
        return bloggerDetails;
    }

    public List<ArticleResponseDetails> viewUserAllHisArticle() {
        Users users = userRepository.findUserByEmail(customUserDetailsService.currentLogedInUserName());
        List<Article> listOfArticle = users.getArticles();
        if (listOfArticle.isEmpty()) {
            throw new CustomException("No article found for this User");
        }

        Type articleDetails = new TypeToken<List<ArticleResponseDetails>>(){}.getType();
        List<ArticleResponseDetails> articleResponseDetailsList = modelMapper.map(listOfArticle, articleDetails);
        return articleResponseDetailsList;
    }

    public ResponseDto deleteUser() {
        try {
            this.userRepository.delete(this.userRepository.findUserByEmail(customUserDetailsService.currentLogedInUserName()));
            return new ResponseDto("Successful", "The User Account has been removed!!");
        }
        catch (Exception e)
        {
            throw new CustomException("User not found!!");
        }
    }
}
