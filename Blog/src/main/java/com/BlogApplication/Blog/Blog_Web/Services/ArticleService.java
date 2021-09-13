package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Security.Services.CustomUserDetailsService;
import com.BlogApplication.Blog.Blog_Web.DTO.ArticleRequestDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.ArticleResponseDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Users;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.ExceptionHandling.CustomException;
import com.BlogApplication.Blog.Blog_Web.Repository.ArticleRepository;
import com.BlogApplication.Blog.Blog_Web.Repository.TagRepository;
import com.BlogApplication.Blog.Blog_Web.Repository.UserRepository;
import com.BlogApplication.Blog.Blog_Web.Utils.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ServiceUtil util;


    public ResponseEntity<List<ArticleResponseDetails>> allArticle() {
        List<Article> article = articleRepository.findAll();
        Type articleDetails = new TypeToken<List<ArticleResponseDetails>>(){}.getType();
        List<ArticleResponseDetails> articleResponseDetailsList = modelMapper.map(article, articleDetails);
        return new ResponseEntity<List<ArticleResponseDetails>>(articleResponseDetailsList, HttpStatus.FOUND);
    }

    @Transactional
    public ResponseEntity<ArticleResponseDetails> createArticle(String tagname,
                                                                ArticleRequestDetails requestArticle)
    {
        Article article= new Article();
        article= modelMapper.map(requestArticle, Article.class);
        List<String> tagFromRequestBody= requestArticle.getTags();

        article.setSlug(article.getTitle().replace(' ', '_')+'_'+article.getArticleId());
        article.setPublishedDate(new Date());
        article.setUpdatedDate(new Date());

        Users users = userRepository.findUserByEmail(customUserDetailsService.currentLogedInUserName());
        article.setUsers(users);

        if(tagname!=null) {
            Tag tag = tagRepository.findByTagName(tagname);
            if(tag==null)
            {
                tag= new Tag(tagname, customUserDetailsService.currentLogedInUserName());
                tagRepository.save(tag);
            }
            tag.addArticleInTag(article);
            article.addTagInArticle(tag);
        }

        if(tagFromRequestBody!=null) {
            for (String requestBodyTag : tagFromRequestBody) {
                Tag newTag = tagRepository.findByTagName(requestBodyTag);
                if (newTag == null) {
                    newTag = new Tag(requestBodyTag, customUserDetailsService.currentLogedInUserName());
                    tagRepository.save(newTag);
                }
                newTag.addArticleInTag(article);
                article.addTagInArticle(newTag);
            }
        }

        this.articleRepository.save(article);

        ArticleResponseDetails articleResponseDetails = modelMapper.map(article, ArticleResponseDetails.class);
        return new ResponseEntity<ArticleResponseDetails>(articleResponseDetails, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseDto> deleteArticle(long articleid) {
        Article article= new Article();
        try {
            article = articleRepository.findById(articleid).get();
        }
        catch (Exception e)
        {
            throw new CustomException("Article not exist!!");
        }
        util.authenticateUserSameAsLogedInUser(article.getUsers().getEmail(), "Not valid User for delete this article!!");
        this.articleRepository.delete(article);

        return new ResponseEntity<>(new ResponseDto(article), HttpStatus.GONE);
    }


    public ResponseEntity<List<ArticleResponseDetails>> getArticleByTag(String tagname) {
        Tag tag= this.tagRepository.findByTagName(tagname);
        if(tag==null)
        {
            throw new CustomException("No Article contain the tag \'"+tagname+"\'");
        }
        List<Article> articles= tag.getArticleList();
        if(articles.isEmpty())
        {
            throw new CustomException("No article found for the tag \'"+tagname+"\'");
        }
        Type articleDetails = new TypeToken<List<ArticleResponseDetails>>(){}.getType();
        List<ArticleResponseDetails> articleResponseDetailsList = modelMapper.map(articles, articleDetails);
        return new ResponseEntity<List<ArticleResponseDetails>>(articleResponseDetailsList,
                                                 HttpStatus.FOUND);
    }

    public ResponseEntity<ArticleResponseDetails> viewArticleBySlug(String slug) {
        Article article=this.articleRepository.findArticleBySlug(slug);
        if(article==null)
        {
            throw new CustomException("No Article found for the slug \'"+slug+"\'");
        }
        ArticleResponseDetails articleResponseDetails = modelMapper.map(article, ArticleResponseDetails.class);
        return new ResponseEntity<ArticleResponseDetails>(articleResponseDetails,
                                    HttpStatus.FOUND);
    }

    public ResponseEntity<ResponseDto> updateArticle(long articleid, Article article) {
        Article newArticle= new Article();
        try {
            newArticle = articleRepository.findById(articleid).get();
        }
        catch(Exception e)
        {
            throw new CustomException("Article Not exist");
        }
        util.authenticateUserSameAsLogedInUser(newArticle.getUsers().getEmail(), "Not valid User for update this article!!");
        String msg= "";
        if(article.getTitle()!=null) {
            msg+=newArticle.getTitle()+" updated to "+article.getTitle()+" ";
            newArticle.setTitle(article.getTitle());
          }
        if(article.getContent()!=null)
        {
            msg+=newArticle.getContent()+" updated to "+article.getContent();
            newArticle.setContent(article.getContent());
        }
        newArticle.setUpdatedDate(new Date());
        this.articleRepository.save(newArticle);
        ResponseDto responseDto= new ResponseDto("Updated Successful",msg);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    public ResponseEntity<ArticleResponseDetails> addTagInArticle(long articleid, String tagname) {
        Article article= new Article();
        try{
            article= articleRepository.findById(articleid).get();
        }
        catch (Exception e)
        {
            throw new CustomException("Article Not exist");
        }
        util.authenticateUserSameAsLogedInUser(article.getUsers().getEmail(), "Not valid User for add tag in this article!!");
        Tag tag= tagRepository.findByTagName(tagname);
        if(tag==null) {
            throw new CustomException("tag not found for tagname \'"+tagname+"\'");
        }
        if(article.getTagList().contains(tag))
        {
            throw new CustomException("Tag \'"+tagname+"\' already present");
        }
        tag.addArticleInTag(article);
        article.addTagInArticle(tag);
        articleRepository.save(article);
        tagRepository.save(tag);

        ArticleResponseDetails articleResponseDetails = modelMapper.map(article, ArticleResponseDetails.class);
        return new ResponseEntity<>(articleResponseDetails,HttpStatus.CREATED);
    }

    public ResponseEntity<List<ArticleResponseDetails>> getArticleByOrder() {
        Pageable pageable= PageRequest.of(0, 3, Sort.by("publishedDate").descending());
        Page<Article> pageOfArticle= this.articleRepository.findPaginatedArticle(pageable);
        if(pageOfArticle==null) {
            throw new CustomException("No Article found!!");
        }
        List<Article> listOfArticle= pageOfArticle.getContent();
        Type articleDetails = new TypeToken<List<ArticleResponseDetails>>(){}.getType();
        List<ArticleResponseDetails> articleResponseDetailsList = modelMapper.map(listOfArticle, articleDetails);
        return new ResponseEntity<List<ArticleResponseDetails>>(articleResponseDetailsList, HttpStatus.FOUND);
    }

    public ResponseEntity<List<Article>> searchArticle(String tagname, String name,String title) {
        List<Article> articleList= articleRepository.findAll();
        List<Article> finalList= new ArrayList<>();
        for(Article article : articleList)
        {
            if((title==null || article.getTitle().equals(title)) && (name==null || article.getUsers().getName().equals(name)))
            {
                List<Tag> taglist= article.getTagList();
                for(Tag tag: taglist)
                {
                    if((tagname==null || tag.getTagname().equals(tagname)) && !finalList.contains(article))
                        finalList.add(article);
                }
            }
        }
        return new ResponseEntity<>(finalList, HttpStatus.FOUND);
    }

}
