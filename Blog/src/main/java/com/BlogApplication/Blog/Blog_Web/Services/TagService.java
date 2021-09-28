package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Security.Helper.JwtUtil;
import com.BlogApplication.Blog.Blog_Security.Services.CustomUserDetailsService;
import com.BlogApplication.Blog.Blog_Web.DTO.TagDetailsDTO;
import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.Exceptions.CustomException;
import com.BlogApplication.Blog.Blog_Web.Message.CustomMessage;
import com.BlogApplication.Blog.Blog_Web.Repository.ArticleRepository;
import com.BlogApplication.Blog.Blog_Web.Repository.TagRepository;
import com.BlogApplication.Blog.Blog_Web.Repository.UserRepository;
import com.BlogApplication.Blog.Blog_Web.Utils.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Deprecated
    public CustomMessage createTag(Tag tag, String email) {
        tag.setCreatedBy(email);
        try{
            this.tagRepository.save(tag);
        }
        catch (Exception e)
        {
            throw new CustomException("Sorry, Tag has not been saved!!");
        }
        return new CustomMessage("Tag "+tag.getTagname() +" has been saved successfully!!");
    }

    public List<TagDetailsDTO> viewAllTag() {
        List<Tag> lagList= this.tagRepository.findAll();
        Type tagDetails = new TypeToken<List<TagDetailsDTO>>(){}.getType();
        List<TagDetailsDTO> tagDetailsDTOList = modelMapper.map(lagList, tagDetails);
        return tagDetailsDTOList;
    }

    @Transactional
    public CustomMessage deleteTag(String tagname) {
        Tag tag=tagRepository.findByTagName(tagname);
        //util.authenticateUserSameAsLogedInUser(tag.getCreatedBy(), "Authentication Failed!!");
        try {
            List<Article> articleList= tag.getArticles();
            for(Article article: articleList)
            {
                articleRepository.delete(article);
                article.getTags().remove(tag);
                articleRepository.save(article);
            }
            this.tagRepository.delete(tag);
        }
        catch (Exception e)
        {
            throw new CustomException("Tag Not delete");
        }
        return new CustomMessage("Tag successfully deleted");
    }

    @Transactional
    public CustomMessage updateTag(String tagname, String newTagName) {
        Tag tag= tagRepository.findByTagName(tagname);
        System.out.println(tag.getTagId());
        util.authenticateUserSameAsLogedInUser(tag.getCreatedBy(), "Authentication Failed!!");

        Tag newTag= new Tag(newTagName, customUserDetailsService.currentLogedInUserName());

        List<Article> articles= userRepository.findUserByEmail(tag.getCreatedBy()).getArticles();
        for(Article article: articles)
        {
            if(article.getTags().contains(tag)) {
                tag.removeArticleFromTag(article);
                article.removeTagFromArticle(tag);
                article.addTagInArticle(newTag);
                newTag.addArticleInTag(article);
            }
        }
        tagRepository.save(newTag);
        if(tag.getArticles().size()==0) tagRepository.delete(tag);
        return  new CustomMessage(tagname +" update to "+newTagName+".");
    }
}
