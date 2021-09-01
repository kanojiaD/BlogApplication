package com.BlogApplication.Blog.Blog_Web.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "TAG")
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tagId;

    @Column( name = "TAG_NAME", unique = true)
    @NotNull(message = "Tag should not be null")
    private String tagName;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @ManyToMany(mappedBy = "tagList")
    private List<Article> articleList= new ArrayList<>();

    public void addArticleInTag(Article article)
    {
        this.articleList.add(article);
    }

    public void removeArticleFromTag(Article article)
    {
        this.articleList.remove(article);
    }

}
