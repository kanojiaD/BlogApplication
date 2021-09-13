package com.BlogApplication.Blog.Blog_Web.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "ARTICLES")
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long articleId;

    private UUID uuid;

    @Column(name = "TITLE")
    @NotNull(message = "Title should not be null")
    private String title;

    @Column(name="CONTENT")
    private String content;

    @Column(name = "SLUG", unique = true)
    private String slug;

    @Column(name = "PUBLISH_DATE", nullable = false)
    private Date publishedDate;

    @Column(name = "UPDATE_DATE", nullable = false)
    private Date updatedDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Tag> tagList= new ArrayList<>();

    public void addTagInArticle(Tag tag)
    {
        this.tagList.add(tag);
    }

    public void removeTagFromArticle(Tag tag)
    {
        this.tagList.remove(tag);
    }
}
