package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Web.DTO.ArticleDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Blogger;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.ExceptionHandling.CustomException;
import com.BlogApplication.Blog.Blog_Web.Repository.ArticleRepository;
import com.BlogApplication.Blog.Blog_Web.Repository.TagRepository;
import com.BlogApplication.Blog.Blog_Web.Repository.UserRepository;
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
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ModelMapper modelMapper;


    public ResponseEntity<List<ArticleDetails>> allArticle() {
        List<Article> article = articleRepository.findAll();
        if(article.isEmpty())
        {
            throw new CustomException("No article found!!");
        }
        Type articleDetails = new TypeToken<List<ArticleDetails>>(){}.getType();
        List<ArticleDetails> articleDetailsList= modelMapper.map(article, articleDetails);
        return new ResponseEntity<List<ArticleDetails>>(articleDetailsList, HttpStatus.FOUND);
    }

    @Transactional
    public ResponseEntity<ArticleDetails> createArticle(long userid, long tagId, Article article) {
        article.setSlug(article.getTitle().replace(' ', '_')+'_'+article.getArticleId());
        article.setPublishedDate(new Date());
        article.setUpdatedDate(new Date());

        Blogger blogger= userRepository.findById(userid).get();
        article.setBlogger(blogger);

        Tag tag=tagRepository.findById(tagId).get();
        tag.addArticleInTag(article);

        article.addTagInArticle(tag);
        this.articleRepository.save(article);

        ArticleDetails articleDetails = modelMapper.map(article, ArticleDetails.class);
        return new ResponseEntity<ArticleDetails>(articleDetails, HttpStatus.CREATED);
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
        this.articleRepository.delete(article);
        return new ResponseEntity<>(new ResponseDto(article), HttpStatus.GONE);
    }


    public ResponseEntity<List<ArticleDetails>> getArticleByTag(String tagname) {
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
        Type articleDetails = new TypeToken<List<ArticleDetails>>(){}.getType();
        List<ArticleDetails> articleDetailsList= modelMapper.map(articles, articleDetails);
        return new ResponseEntity<List<ArticleDetails>>(articleDetailsList,
                                                 HttpStatus.FOUND);
    }

    public ResponseEntity<ArticleDetails> viewArticleBySlug(String slug) {
        Article article=this.articleRepository.findArticleBySlug(slug);
        if(article==null)
        {
            throw new CustomException("No Article found for the slug \'"+slug+"\'");
        }
        ArticleDetails articleDetails= modelMapper.map(article,ArticleDetails.class);
        return new ResponseEntity<ArticleDetails>(articleDetails,
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
        String msg= "";
        if(article.getTitle()!=null) {
            msg+=newArticle.getTitle()+" updated to "+article.getTitle()+" ";
            msg+=newArticle.getSlug()+" updated to "+article.getTitle().replace(' ', '_')+'_'+newArticle.getArticleId()+" ";
            newArticle.setTitle(article.getTitle());
            newArticle.setSlug(article.getTitle().replace(' ', '_')+'_'+newArticle.getArticleId());
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

    public ResponseEntity<ArticleDetails> addTagInArticle(long articleid, String tagname) {
        Article article= new Article();
        try{
            article= articleRepository.findById(articleid).get();
        }
        catch (Exception e)
        {
            throw new CustomException("Article Not exist");
        }
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

        ArticleDetails articleDetails= modelMapper.map(article,ArticleDetails.class);
        return new ResponseEntity<>(articleDetails,HttpStatus.CREATED);
    }

    public ResponseEntity<List<ArticleDetails>> getArticleByOrder() {
        Pageable pageable= PageRequest.of(0, 3, Sort.by("publishedDate").descending());
        Page<Article> pageOfArticle= this.articleRepository.findPaginatedArticle(pageable);
        if(pageOfArticle==null) {
            throw new CustomException("No Article found!!");
        }
        List<Article> listOfArticle= pageOfArticle.getContent();
        Type articleDetails = new TypeToken<List<ArticleDetails>>(){}.getType();
        List<ArticleDetails> articleDetailsList= modelMapper.map(listOfArticle, articleDetails);
        return new ResponseEntity<List<ArticleDetails>>(articleDetailsList, HttpStatus.FOUND);
    }

    public ResponseEntity<List<Article>> searchArticle(String tagname, String username,String title) {
        List<Article> articleList= articleRepository.findAll();
        List<Article> finalList= new ArrayList<>();
        for(Article article : articleList)
        {
            if((title==null || article.getTitle().equals(title)) && (username==null || article.getBlogger().getUsername().equals(username)))
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
