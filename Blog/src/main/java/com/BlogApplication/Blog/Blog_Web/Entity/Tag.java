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
    @NotNull(message = "Tag should not be null")
    private String tagname;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
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
