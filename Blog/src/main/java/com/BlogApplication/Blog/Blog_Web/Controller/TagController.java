package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.DTO.TagDetails;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.Message.BlogMessage;
import com.BlogApplication.Blog.Blog_Web.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
public class TagController {

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
    public ResponseEntity<ResponseDto> createTag(@Valid @RequestBody Tag tag)
    {
        return this.tagService.createTag(tag);
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
}
