package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Security.Services.CustomUserDetailsService;
import com.BlogApplication.Blog.Blog_Web.DTO.ArticleRequestDTO;
import com.BlogApplication.Blog.Blog_Web.DTO.ArticleResponseDTO;
import com.BlogApplication.Blog.Blog_Web.Data.DummyData;
import com.BlogApplication.Blog.Blog_Web.Entity.*;
import com.BlogApplication.Blog.Blog_Web.Exceptions.ArticleNotFoundException;
import com.BlogApplication.Blog.Blog_Web.Exceptions.CustomException;
import com.BlogApplication.Blog.Blog_Web.Message.CustomMessage;
import com.BlogApplication.Blog.Blog_Web.Repository.ArticleRepository;
import com.BlogApplication.Blog.Blog_Web.Repository.TagRepository;
import com.BlogApplication.Blog.Blog_Web.Repository.UserRepository;
import com.BlogApplication.Blog.Blog_Web.Utils.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    TagService tagService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ServiceUtil util;
    @Autowired
    DummyData data;


//    public List<ArticleResponseDTO> allArticle() {
//        List<Article> articles = articleRepository.findAll();
//
//        List<ArticleResponseDTO> articleResponseDTOs =new ArrayList<>();
//        for(Article article: articles)
//        {
//            ArticleResponseDTO dto = modelMapper.map(article, ArticleResponseDTO.class);
//            dto.setAuthor(article.getUsers().getEmail());
//            articleResponseDTOs.add(dto);
//        }
//        return articleResponseDTOs;
//    }



    @Transactional
    public ArticleResponseDTO createArticle(String tagname,
                                            ArticleRequestDTO requestedArticle)
    {
        Article article= new Article();

        article= modelMapper.map(requestedArticle, Article.class);
        List<String> tagFromRequestBody= requestedArticle.getTag();
        String id=util.generateUniqueID();
        article.setPostId(id);
        article.setPublishedOn(new Timestamp(System.currentTimeMillis()));
        article.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
        Users users = userRepository.findUserByEmail(customUserDetailsService.currentLogedInUserName());
        article.setUsers(users);

        Terms terms= new Terms(util.generateSlug(article.getTitle(), id));
        terms.setArticle(article);
        article.addTerms(terms);

        /**
         * below tagname is optional which are taken from query parameter.
         * it would be null if don't send the tagname.
         */
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

        /**
         * below list of tags are taking from @RequestBody.
         * if the variable is missing then tags variable would be null
         * otherwise, it would be empty or containing some tag name.
         */
        if(tagFromRequestBody!=null)
        {
            for (String tagToBeAdded : tagFromRequestBody) {
                Tag getTag = tagRepository.findByTagName(tagToBeAdded);
                if (getTag == null)
                {
                    getTag = new Tag(tagToBeAdded, customUserDetailsService.currentLogedInUserName());
                    tagRepository.save(getTag);
                }
                getTag.addArticleInTag(article);
                article.addTagInArticle(getTag);
            }
        }
        this.articleRepository.save(article);
        ArticleResponseDTO articleResponseDTO = modelMapper.map(article, ArticleResponseDTO.class);
        articleResponseDTO.setAuthor(article.getUsers().getEmail());
        return articleResponseDTO;
    }



    @Transactional
    public CustomMessage deleteArticle(long articleid) {
        Article article= new Article();
        try {
            article = articleRepository.findById(articleid).get();
        }
        catch (Exception e)
        {
            throw new ArticleNotFoundException();
        }
        
        util.authenticateUserSameAsLogedInUser(article.getUsers().getEmail(), "Not valid User for delete this article!!");
        List<Tag> tags= new ArrayList<>();
        tags.addAll(article.getTags());
        for(Tag tag : tags)
        {
            tag.removeArticleFromTag(article);
            article.removeTagFromArticle(tag);
        }
        this.articleRepository.delete(article);
        for(Tag tag:tags)
        {
            if((tag.getArticles().size()==1 && tag.getArticles().get(0).getArticleId()==articleid)||tag.getArticles().size()==0)
            {
                tagService.deleteTag(tag.getTagname());
            }
        }
        return new CustomMessage(article.getTitle()+" has been removed successfully!!");
    }



//    public List<ArticleResponseDTO> getArticleByTag(String tagname) {
//        Tag tag= this.tagRepository.findByTagName(tagname);
//        if(tag==null)
//        {
//            throw new CustomException("No Article contain the tag \'"+tagname+"\'");
//        }
//        List<Article> articles= tag.getArticles();
//        if(articles.isEmpty())
//        {
//            throw new CustomException("No article found for the tag \'"+tagname+"\'");
//        }
//
//        List<ArticleResponseDTO> articleResponseDTOs =new ArrayList<>();
//        for(Article article: articles)
//        {
//            ArticleResponseDTO dto = modelMapper.map(article, ArticleResponseDTO.class);
//            dto.setAuthor(article.getUsers().getEmail());
//            articleResponseDTOs.add(dto);
//        }
//        return articleResponseDTOs;
//    }



    public ArticleResponseDTO viewArticleBySlug(String slug) {
        String[] splittedSlug= slug.split("-");
        Article article=this.articleRepository.findByPostID(splittedSlug[splittedSlug.length-1]);
        if(article==null)
        {
            throw new ArticleNotFoundException();
        }
        ArticleResponseDTO articleResponseDTO = modelMapper.map(article, ArticleResponseDTO.class);
        articleResponseDTO.setAuthor(article.getUsers().getEmail());
        return articleResponseDTO;
    }

    public CustomMessage updateArticle(long articleid, Article article) {
        Article newArticle= new Article();
        try {
            newArticle = articleRepository.findById(articleid).get();
        }
        catch(Exception e)
        {
            throw new ArticleNotFoundException();
        }
        util.authenticateUserSameAsLogedInUser(newArticle.getUsers().getEmail(), "Not valid User for update this article!!");
        String message= "";
        if(article.getTitle()!=null) {
            Terms terms= new Terms(util.generateSlug(article.getTitle(), newArticle.getPostId()));
            newArticle.addTerms(terms);
            newArticle.setTitle(article.getTitle());
            message+=newArticle.getTitle()+" updated to "+article.getTitle()+" ";
          }
        if(article.getContent()!=null)
        {
            message+=newArticle.getContent()+" updated to "+article.getContent();
            newArticle.setContent(article.getContent());
        }
        newArticle.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
        this.articleRepository.save(newArticle);
        return new CustomMessage(message);
    }

    public ArticleResponseDTO addTagInArticle(long articleid, String tagname) {
        Article article= new Article();
        try{
            article= articleRepository.findById(articleid).get();
        }
        catch (Exception e)
        {
            throw new ArticleNotFoundException();
        }
        util.authenticateUserSameAsLogedInUser(article.getUsers().getEmail(), "Not valid User for add tag in this article!!");
        Tag tag= tagRepository.findByTagName(tagname);
        if(tag==null) {
            tag= new Tag(tagname, customUserDetailsService.currentLogedInUserName());
        }
        if(article.getTags().contains(tag))
        {
            throw new CustomException("Tag \'"+tagname+"\' already present in article "+article.getTitle());
        }
        tag.addArticleInTag(article);
        article.addTagInArticle(tag);
        articleRepository.save(article);

        ArticleResponseDTO articleResponseDTO = modelMapper.map(article, ArticleResponseDTO.class);
        return articleResponseDTO;
    }

    public List<ArticleResponseDTO> getArticleByOrder(Integer pagenumber, Integer pagesize) {
        if(pagesize==null) pagesize=3;
        Pageable pageable= PageRequest.of(pagenumber, pagesize, Sort.by("publishedDate").descending());
        Page<Article> pageOfArticle= this.articleRepository.findPaginatedArticle(pageable);
        if(pageOfArticle==null) {
            throw new ArticleNotFoundException();
        }
        List<Article> articles= pageOfArticle.getContent();
        List<ArticleResponseDTO> articleResponseDTOs =new ArrayList<>();
        for(Article article: articles)
        {
            ArticleResponseDTO dto = modelMapper.map(article, ArticleResponseDTO.class);
            dto.setAuthor(article.getUsers().getEmail());
            articleResponseDTOs.add(dto);
        }
        return articleResponseDTOs;
    }

    public List<ArticleResponseDTO> searchArticle(List<String> tags, String name, String title) {
        List<Article> articles= articleRepository.findAll();
        List<Article> finalList= new ArrayList<>();
        for(Article article : articles)
        {
            if((title==null || article.getTitle().toLowerCase().contains(title.toLowerCase())) && (name==null || article.getUsers().getName().equalsIgnoreCase(name)))
            {
                List<Tag> taglist= article.getTags();
                for(Tag tag: taglist)
                {
                    if((tags==null || tags.contains(tag.getTagname())) && !finalList.contains(article))
                        finalList.add(article);
                }
            }
        }
        List<ArticleResponseDTO> articleResponseDTOs =new ArrayList<>();
        for(Article article: finalList)
        {
            ArticleResponseDTO dto = modelMapper.map(article, ArticleResponseDTO.class);
            dto.setAuthor(article.getUsers().getEmail());
            articleResponseDTOs.add(dto);
        }
        return articleResponseDTOs;
    }

    public CustomMessage removeArticlesTag(Long id, String tagname) {
        Article article= articleRepository.getById(id);
        Tag tag= tagRepository.findByTagName(tagname);
        article.removeTagFromArticle(tag);
        tag.removeArticleFromTag(article);
        if(tag.getArticles().size()==0) tagRepository.delete(tag);
        return new CustomMessage(tagname+" tag removed from "+article.getTitle());
    }

    public void addDummydata() {
        //data.addDummyUsers();
        data.addDummyArticles1();
    }
}
