package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Security.Helper.JwtUtil;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.DTO.TagDetails;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
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
    public ResponseEntity<ResponseDto> createTag(@Valid @RequestBody Tag tag,
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
    public ResponseEntity<List<TagDetails>>  viewAllTag()
    {
        return new ResponseEntity<List<TagDetails>>(this.tagService.viewAllTag(), HttpStatus.FOUND);
    }

    /**
     * Delete tag.
     */
    @DeleteMapping("/blog/tag/{tagname}/")
    public ResponseEntity<ResponseDto>deleteTag(@PathVariable String tagname)
    {
        return new ResponseEntity<ResponseDto>(this.tagService.deleteTag(tagname),
                HttpStatus.GONE);
    }

    /**
     * update tag
     * @param ?tag=presentTag&update=someTag
     */
    @PutMapping("/blog/tag/")
    public ResponseEntity<ResponseDto> updateTag(@RequestParam(value = "tag") String tagname,
                            @RequestParam(value = "update") String newTagName)
    {
        return new ResponseEntity<ResponseDto>(this.tagService.updateTag(tagname,newTagName),
                                                HttpStatus.CREATED);
    }

}
