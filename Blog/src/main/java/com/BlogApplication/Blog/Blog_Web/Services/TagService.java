package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public Tag createTag(Tag tag) {
        //this.tagRepository.save(tag);
        return tag;
    }


}
