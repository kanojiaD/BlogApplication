package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Security.Helper.JwtUtil;
import com.BlogApplication.Blog.Blog_Security.Services.CustomUserDetailsService;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.DTO.TagDetails;
import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.ExceptionHandling.CustomException;
import com.BlogApplication.Blog.Blog_Web.Message.BlogMessage;
import com.BlogApplication.Blog.Blog_Web.Repository.ArticleRepository;
import com.BlogApplication.Blog.Blog_Web.Repository.TagRepository;
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


@Service
public class TagService {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    ServiceUtil util;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;

    public ResponseDto createTag(Tag tag, String email) {
        tag.setCreatedBy(email);
        try{
            this.tagRepository.save(tag);
        }
        catch (Exception e)
        {
            throw new CustomException("Sorry, Tag has not been saved!!");
        }
        return new ResponseDto("Successful","Tag "+tag.getTagname() +" has been saved successfully!!");
    }

    public List<TagDetails> viewAllTag() {
        List<Tag> lagList= this.tagRepository.findAll();
        if(lagList.isEmpty())
        {
            throw new CustomException(" Tag not found!!");
        }
        Type tagDetails = new TypeToken<List<TagDetails>>(){}.getType();
        List<TagDetails> tagDetailsList = modelMapper.map(lagList, tagDetails);
        return tagDetailsList;
    }

    @Transactional
    public ResponseDto deleteTag(String tagname) {
        Tag tag=tagRepository.findByTagName(tagname);
        util.authenticateUserSameAsLogedInUser(tag.getCreatedBy(), "Authentication Failed!!");
        try {
            List<Article> articleList= tag.getArticleList();
            for(Article article: articleList)
            {
                articleRepository.delete(article);
                article.getTagList().remove(tag);
                articleRepository.save(article);
            }
            this.tagRepository.delete(tag);
        }
        catch (Exception e)
        {
            throw new CustomException("Failed");
        }
        return new ResponseDto("Successful", "Tag successfully deleted");
    }

    public ResponseDto updateTag(String tagname, String newTagName) {
        Tag tag= tagRepository.findByTagName(tagname);
        System.out.println(tag.getTagId());
        util.authenticateUserSameAsLogedInUser(tag.getCreatedBy(), "Authentication Failed!!");

        Tag newTag= new Tag(newTagName, customUserDetailsService.currentLogedInUserName());

        List<Article> articles= userRepository.findUserByEmail(tag.getCreatedBy()).getListOfArticle();
        for(Article article: articles)
        {
            if(article.getTagList().contains(tag)) {
                tag.removeArticleFromTag(article);
                article.removeTagFromArticle(tag);
                article.addTagInArticle(newTag);
                newTag.addArticleInTag(article);
            }
        }
        tagRepository.save(newTag);
        if(tag.getArticleList().size()==0) tagRepository.delete(tag);
        return new ResponseDto("update", tagname +" update to "+newTagName+"." );
    }
}
