package com.BlogApplication.Blog.Blog_Web.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Table(name = "TAG")
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tagId;

    @Column( name = "TAG_NAME", unique = true)
    @NotNull(message = "Tag Name should not be null")
    private String tagname;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Article> Articles= new ArrayList<>();

    public void addArticleInTag(Article article)
    {
        this.Articles.add(article);
    }

    public void removeArticleFromTag(Article article)
    {
        this.Articles.remove(article);
    }

    public Tag(String tagname, String createdBy) {
        this.tagname = tagname;
        this.createdBy = createdBy;
    }
}
