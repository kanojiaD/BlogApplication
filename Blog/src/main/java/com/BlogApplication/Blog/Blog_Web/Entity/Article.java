package com.BlogApplication.Blog.Blog_Web.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "ARTICLES")
@Entity
public class Article {

    @Id
    private Long articleId;

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
    private Blogger blogger;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
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
