package com.BlogApplication.Blog.Blog_Web.DTO;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDTO {
    private Date updatedOn;
    private Long articleId;
    private String author;
    private String title;
    private String content;
    private Set<TagDTO> tags;
}
