package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Security.Helper.JwtUtil;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TagService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    TagRepository tagRepository;

    public ResponseEntity<Tag> createTag(Tag tag) {
        tag.setCreatedBy("dharmendra@gmail.com");
        this.tagRepository.save(tag);
        return new ResponseEntity<Tag>(tag, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Tag>> viewAllTag() {
        return new ResponseEntity<>(this.tagRepository.findAll(), HttpStatus.FOUND);
    }
}
