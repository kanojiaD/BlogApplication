package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping("/blog/tag/")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag)
    {
        return this.tagService.createTag(tag);
    }

    @GetMapping("/blog/tag/")
    public ResponseEntity<List<Tag>>  viewAllTag()
    {
        return this.tagService.viewAllTag();
    }
}
