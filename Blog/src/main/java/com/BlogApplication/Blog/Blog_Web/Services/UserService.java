package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Security.Services.CustomUserDetailsService;
import com.BlogApplication.Blog.Blog_Web.DTO.ArticleResponseDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.BloggerDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Users;
import com.BlogApplication.Blog.Blog_Web.ExceptionHandling.CustomException;
import com.BlogApplication.Blog.Blog_Web.Repository.UserRepository;
import com.BlogApplication.Blog.Blog_Web.Utils.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Transactional
    public ResponseEntity<ResponseDto> userRegistration(Users users) {
        users.setUserid(12L);
        users.setUserUUID(new UUID(9223372036854775807L, -9223372036854775808L));
        if(users.getRole()== null) users.setRole("ROLE_USER");
        this.userRepository.save(users);
        return new ResponseEntity<ResponseDto>(new ResponseDto(users), HttpStatus.CREATED);
    }

    public ResponseEntity<BloggerDetails> getUser() {
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
        return new ResponseEntity<BloggerDetails>(bloggerDetails, HttpStatus.OK);
    }

    public ResponseEntity<List<ArticleResponseDetails>> viewUserAllHisArticle() {
        Users users = userRepository.findUserByEmail(customUserDetailsService.currentLogedInUserName());
        List<Article> listOfArticle = users.getListOfArticle();
        if (listOfArticle.isEmpty()) {
            throw new CustomException("No article found for this User");
        }

        Type articleDetails = new TypeToken<List<ArticleResponseDetails>>(){}.getType();
        List<ArticleResponseDetails> articleResponseDetailsList = modelMapper.map(listOfArticle, articleDetails);
        return new ResponseEntity<List<ArticleResponseDetails>>(articleResponseDetailsList, HttpStatus.FOUND);
    }

    public ResponseEntity<ResponseDto> deleteUser(Long userid) {
        util.authenticateUserSameAsLogedInUser(userRepository.getById(userid).getEmail(), "Not valid User for delete the account!!");
        try {
            this.userRepository.delete(this.userRepository.getById(userid));
            return new ResponseEntity<>(new ResponseDto("Successful", "The User Account has been removed!!"), HttpStatus.GONE);
        }
        catch (Exception e)
        {
            throw new CustomException("User not found!!");
        }
    }
}
