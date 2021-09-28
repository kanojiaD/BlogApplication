package com.BlogApplication.Blog.Blog_Web.Data;

import com.BlogApplication.Blog.Blog_Web.DTO.ArticleRequestDTO;
import com.BlogApplication.Blog.Blog_Web.Entity.Users;
import com.BlogApplication.Blog.Blog_Web.Services.ArticleService;
import com.BlogApplication.Blog.Blog_Web.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DummyData {
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;

    public void addDummyUsers()
    {
        userService.userRegistration(new Users("Rakesh Chaudhry", "rakeshchaudhary598@gmail.com", "rakesh123", "6393099784"));
        userService.userRegistration(new Users("Shan Mohhammed", "shan1.mohammed@ril.com", "shan1234", "7904766024"));
        userService.userRegistration(new Users("varun verma", "varun3.verma@ril.com", "varun123", "9560426941"));
        userService.userRegistration(new Users("pradeep kumar", "pradeep57.kumar@ril.com", "pradeep123", "9321901048"));
        userService.userRegistration(new Users("Rishabh", "rishabh123@gmail.com", "rishabh123", "9621884592"));
    }
    public void addDummyArticles1()
    {
        articleService.createArticle(null, new ArticleRequestDTO("Hiding in plain sight","some Content", Arrays.asList("Machine Learning", "OpenCV")));
        articleService.createArticle(null, new ArticleRequestDTO("Object Detection at the Edge with TF lite model-maker","some Content", Arrays.asList("Machine Learning", "OpenCV", "Neural Network")));
        articleService.createArticle(null, new ArticleRequestDTO("Understanding Hypothesis Testing","some Content", Arrays.asList("Data Science","Python Statistics", "Data Analysis", "OpenCV")));
    }
    public void addDummyArticles2()
    {
        articleService.createArticle(null, new ArticleRequestDTO("Make your very first Docker Image","some Content", Arrays.asList("Docker", "Dockerfiles", "Programming")));
        articleService.createArticle(null, new ArticleRequestDTO("Event Loop Blocking in Node.js? Not Anymore!","some Content", Arrays.asList("Nodejs" , "Event Loop")));
        articleService.createArticle(null, new ArticleRequestDTO("Basics of Software Testing","some Content", Arrays.asList("Software Development" , "Software Testing" , "Software" , "Software Engineering")));
    }
    public void addDummyArticles3()
    {
        articleService.createArticle(null, new ArticleRequestDTO("Secure a Spring Boot REST API with JSON Web Token","some Content", Arrays.asList("Spring Boot", "RestAPI", "JSON Web Token")));
        articleService.createArticle(null, new ArticleRequestDTO("Role Based Access Control (RBAC) with Spring Boot and JWT","some Content", Arrays.asList("Spring Boot", "JSON Web Token")));
        articleService.createArticle(null, new ArticleRequestDTO("Spring Sessions with Apache Ignite","some Content", Arrays.asList("Spring Boot" ,"Apache Ignite","Java")));
    }
    public void addDummyArticles4()
    {
        articleService.createArticle(null, new ArticleRequestDTO("Apple And Google Do The Work Of Putin","some Content", Arrays.asList("Apple","Google" ,"Democracy")));
        articleService.createArticle(null, new ArticleRequestDTO("Capitalism Is the Engine of Climate Change","some Content", Arrays.asList("Capitalism", "Climate Change")));
        articleService.createArticle(null, new ArticleRequestDTO("Senate Democrats Unveil the ‘Freedom to Vote’ Act","some Content", Arrays.asList("Voting Rights" ,"Freedom To Vote Act" ,"Democratic Senators" ,"Voting")));
    }
    public void addDummyArticles5()
    {
        articleService.createArticle(null, new ArticleRequestDTO("Four Fake Nice Gestures That Are Actually Manipulative","some Content", Arrays.asList("Life", "Fake Gestures")));
        articleService.createArticle(null, new ArticleRequestDTO("We’re Moving. Fire And Money Are Driving Us Away.","some Content", Arrays.asList("Fire", "Money", "Driving Away")));
        articleService.createArticle(null, new ArticleRequestDTO("Let’s Build a Blog With Django + Python","some Content", Arrays.asList("Django", " Python")));
    }
}