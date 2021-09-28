package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Security.Helper.JwtUtil;
import com.BlogApplication.Blog.Blog_Web.DTO.TagDetailsDTO;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.Message.CustomMessage;
import com.BlogApplication.Blog.Blog_Web.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * @deprecated
     * @return : tag
     */
    @PostMapping("/blog/tag/")
    public ResponseEntity<CustomMessage> createTag(@Valid @RequestBody Tag tag,
                                                   @RequestHeader(value = "Authorization") String authToken)
    {
        return new ResponseEntity<>(this.tagService.createTag(tag, jwtUtil.extractUsername(authToken.substring(7))),
                HttpStatus.CREATED);
    }

    /**
     * 2.
     * This API is use for view all tag.
     * Authentication required.
     * @return : List of All Tag
     */
    @GetMapping("/blog/tag/")
    public ResponseEntity<List<TagDetailsDTO>>  viewAllTag()
    {
        return new ResponseEntity<List<TagDetailsDTO>>(this.tagService.viewAllTag(), HttpStatus.FOUND);
    }

    /**
     * Delete tag.
     */
    @DeleteMapping("/blog/tag/{tagname}/")
    public ResponseEntity<CustomMessage>deleteTag(@PathVariable String tagname)
    {
        return new ResponseEntity<>(this.tagService.deleteTag(tagname),
                HttpStatus.GONE);
    }

    /**
     * update tag
     * @param ?tag=presentTag&update=someTag
     */
    @PutMapping("/blog/tag/")
    public ResponseEntity<CustomMessage> updateTag(@RequestParam(value = "tag") String tagname,
                                               @RequestParam(value = "update") String newTagName)
    {
        return new ResponseEntity<>(this.tagService.updateTag(tagname,newTagName),
                                                HttpStatus.CREATED);
    }
}
