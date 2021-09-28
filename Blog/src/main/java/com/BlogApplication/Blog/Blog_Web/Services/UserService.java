package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Security.Services.CustomUserDetailsService;
import com.BlogApplication.Blog.Blog_Web.DTO.ArticleResponseDTO;
import com.BlogApplication.Blog.Blog_Web.DTO.UserDTO;
import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Users;
import com.BlogApplication.Blog.Blog_Web.Exceptions.CustomException;
import com.BlogApplication.Blog.Blog_Web.Message.CustomMessage;
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
    public CustomMessage userRegistration(Users users) {
        if(!users.getEmail().toLowerCase().endsWith(".com"))
        {
            return new CustomMessage("Email must be ends with '.com'");
        }
        String message= "email id is "+users.getEmail()+" and password is "+users.getPassword();
        users.setUserUUID(UUID.randomUUID().toString());
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        if(users.getRole()== null) users.setRole("ROLE_USER");
        this.userRepository.save(users);
        return new CustomMessage(message);
    }

    public UserDTO getUser() {
        Users users = new Users();
        try
        {
             users = userRepository.findUserByEmail(customUserDetailsService.currentLogedInUserName());
        }
        catch(Exception e)
        {
            throw new CustomException("User not found");
        }
        UserDTO userDTO = modelMapper.map(users, UserDTO.class);
        return userDTO;
    }

    public List<ArticleResponseDTO> viewUserAllHisArticle() {
        Users users = userRepository.findUserByEmail(customUserDetailsService.currentLogedInUserName());
        List<Article> listOfArticle = users.getArticles();
        Type articleDetails = new TypeToken<List<ArticleResponseDTO>>(){}.getType();
        List<ArticleResponseDTO> articleResponseDTOList = modelMapper.map(listOfArticle, articleDetails);
        return articleResponseDTOList;
    }

    public CustomMessage deleteUser() {
        try {
            Users user=this.userRepository.findUserByEmail(customUserDetailsService.currentLogedInUserName());
            this.userRepository.delete(user);
            return new CustomMessage(user.getEmail()+" account has been removed!!");
        }
        catch (Exception e)
        {
            throw new CustomException("User not found!!");
        }
    }
}
