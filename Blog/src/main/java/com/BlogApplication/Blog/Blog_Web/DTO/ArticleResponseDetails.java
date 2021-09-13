package com.BlogApplication.Blog.Blog_Web.DTO;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDetails {
    private Date updatedDate;
    private String title;
    private String content;
    private Set<TagDto> tag;
}
