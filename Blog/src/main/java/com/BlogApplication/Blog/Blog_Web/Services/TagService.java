package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Security.Helper.JwtUtil;
import com.BlogApplication.Blog.Blog_Web.DTO.ResponseDto;
import com.BlogApplication.Blog.Blog_Web.DTO.TagDetails;
import com.BlogApplication.Blog.Blog_Web.Entity.Tag;
import com.BlogApplication.Blog.Blog_Web.ExceptionHandling.CustomException;
import com.BlogApplication.Blog.Blog_Web.Message.BlogMessage;
import com.BlogApplication.Blog.Blog_Web.Repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;


@Service
public class TagService {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TagRepository tagRepository;

    public ResponseEntity<ResponseDto> createTag(Tag tag, String email) {
        tag.setCreatedBy(email);
        try{
            this.tagRepository.save(tag);
        }
        catch (Exception e)
        {
            throw new CustomException("Sorry, Tag has not been saved!!");
        }
        return new ResponseEntity<>(new ResponseDto("Successful","Tag "+tag.getTagname() +" has been saved successfully!!"),
                HttpStatus.CREATED);
    }

    public ResponseEntity<List<TagDetails>> viewAllTag() {
        List<Tag> lagList= this.tagRepository.findAll();
        if(lagList.isEmpty())
        {
            throw new CustomException(" Tag not found!!");
        }
        Type tagDetails = new TypeToken<List<TagDetails>>(){}.getType();
        List<TagDetails> tagDetailsList = modelMapper.map(lagList, tagDetails);
        return new ResponseEntity<List<TagDetails>>(tagDetailsList, HttpStatus.FOUND);
    }

    public ResponseEntity<ResponseDto> deleteTag(String tagname, String email) {
        Tag tag=tagRepository.findByTagName(tagname);
        if(!tag.getCreatedBy().equals(email)){
            throw new CustomException("This user doesn't have permission to delete this tag.");
        }
        try {
            this.tagRepository.delete(tag);
        }
        catch (Exception e)
        {
            throw new CustomException("This tag is already used!!. So you can not delete this tag");
        }
        return new ResponseEntity<ResponseDto>(new ResponseDto("Successful", "Tag successfully deleted"),
                                                HttpStatus.GONE);
    }
}
