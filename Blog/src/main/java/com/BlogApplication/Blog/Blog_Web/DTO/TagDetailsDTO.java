package com.BlogApplication.Blog.Blog_Web.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TagDetailsDTO {
    private String tagname;
    private String createdBy;
}
