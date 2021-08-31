package com.BlogApplication.Blog.Blog_Web.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "TAG")
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tagId;

    @Column( name = "TAG_NAME", unique = true)
    @NotNull(message = "Tag should not be null")
    private String tagName;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @ManyToMany(mappedBy = "tagList", fetch = FetchType.LAZY)
    private List<Article> articleList;
}
