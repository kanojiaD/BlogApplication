package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.DTO.ArticleResponseDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.BloggerDetails;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.Entity.Users;
import com.BlogApplication.Blog.Blog_Web.ExceptionHandling.CustomException;
import com.BlogApplication.Blog.Blog_Web.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

 @Autowired
    UserService userService;

    /**
     * 1.
     * This API is use for User Registration.
     * Authentication not required.
     * @RequestBody : blogger
     * @return : A message that registration is successful or not.
     */
    @PostMapping("/blog/signup/")
    public ResponseEntity<ResponseDto> userRegistration(@Valid @RequestBody Users users)
    {
        try{
            return this.userService.userRegistration(users);
        }
        catch (Exception e)
        {
            throw new CustomException("Registration failed!!");
        }
    }

    /**
     * 2.
     * User can see the own details using this API.
     * Authentication required.
     * @Pathvariable : userid
     * @return : Blogger Details
     */
    @GetMapping("/blog/user/")
    public ResponseEntity<BloggerDetails> getUser()
    {
        return this.userService.getUser();
    }

    /**
     * 3.
     * Using this API user can see all his article.
     * Authentication required.
     * @Pathvariable : userid
     * @return : List of Article
     */
    @GetMapping("/blog/user/articles/")
    public ResponseEntity<List<ArticleResponseDetails>> viewUserAllHisArticle()
    {
        return this.userService.viewUserAllHisArticle();
    }

    /**
     * 4.
     *  This API is use for delete the account of User.
     *  Authentication required.
     * @return : ResponseDto.
     */
    @DeleteMapping("/blog/user/{userid}/")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable("userid") Long userid)
    {
        return this.userService.deleteUser(userid);
    }

}
