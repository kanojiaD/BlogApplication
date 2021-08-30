package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping("/blog/tag/")
    public Tag createTag(@RequestBody Tag tag)
    {
        return this.tagService.createTag(tag);
    }

}
