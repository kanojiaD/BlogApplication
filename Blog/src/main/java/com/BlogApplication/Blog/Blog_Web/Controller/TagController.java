package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Security.Helper.JwtUtil;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.DTO.TagDetails;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.Message.BlogMessage;
import com.BlogApplication.Blog.Blog_Web.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class TagController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    TagService tagService;

    /**
     * 1.
     * This API is use for create tag.
     * Authentication required.
     * @RequestBody : tag
     * @return : tag
     */
    @PostMapping("/blog/tag/")
    public ResponseEntity<ResponseDto> createTag(@Valid @RequestBody Tag tag,
                                                 @RequestHeader(value = "Authorization") String authToken)
    {
        return this.tagService.createTag(tag, jwtUtil.extractUsername(authToken.substring(7)));
    }

    /**
     * 2.
     * This API is use for view all tag.
     * Authentication required.
     * @return : List of All Tag
     */
    @GetMapping("/blog/tag/")
    public ResponseEntity<List<TagDetails>>  viewAllTag()
    {
        return this.tagService.viewAllTag();
    }

    /**
     * This API is use for Delete tag.
     */
    @DeleteMapping("/blog/tag/{tagname}/")
    public ResponseEntity<ResponseDto>deleteTag(@PathVariable String tagname)
    {
        return this.tagService.deleteTag(tagname);
    }

}
