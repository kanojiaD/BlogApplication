package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Web.DTO.ArticleDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.BloggerDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Blogger;
import com.BlogApplication.Blog.Blog_Web.ExceptionHandling.CustomException;
import com.BlogApplication.Blog.Blog_Web.Repository.UserRepository;
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

    @Transactional
    public ResponseEntity<ResponseDto> userRegistration(Blogger blogger) {
        blogger.setUserid(12L);
        blogger.setUserUUID(new UUID(9223372036854775807L, -9223372036854775808L));
        if(blogger.getRole()== null) blogger.setRole("ROLE_USER");
        this.userRepository.save(blogger);
        return new ResponseEntity<ResponseDto>(new ResponseDto(blogger), HttpStatus.CREATED);
    }

    public ResponseEntity<BloggerDetails> getBlogger(Long userid) {
        Blogger blogger= new Blogger();
        try
        {
             blogger = userRepository.findById(userid).get();
        }
        catch(Exception e)
        {
            throw new CustomException("User not found");
        }
        BloggerDetails bloggerDetails= modelMapper.map(blogger, BloggerDetails.class);
        return new ResponseEntity<BloggerDetails>(bloggerDetails, HttpStatus.OK);
    }

    public ResponseEntity<List<ArticleDetails>> viewUserAllHisArticle(long userid) {
        Blogger blogger = userRepository.getById(userid);
        List<Article> listOfArticle = blogger.getListOfArticle();
        if (listOfArticle.isEmpty()) {
            throw new CustomException("No article found for this User");
        }

        Type articleDetails = new TypeToken<List<ArticleDetails>>(){}.getType();
        List<ArticleDetails> articleDetailsList= modelMapper.map(listOfArticle, articleDetails);
        return new ResponseEntity<List<ArticleDetails>>(articleDetailsList, HttpStatus.FOUND);
    }
}
