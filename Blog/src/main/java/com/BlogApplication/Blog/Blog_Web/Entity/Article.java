package com.BlogApplication.Blog.Blog_Web.Entity;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ARTICLES")
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String articleId;

    @Column(name = "TITLE")
    @NotNull(message = "Title should not be null")
    private String title;

    @Column(name="CONTENT")
    private String content;

    @Column(name = "SLAG", unique = true)
    @UniqueElements(message = "slag should be unique")
    private String slag;

    @Column(name = "PUBLISH_DATE", nullable = false)
    private Date publishedDate;

    @Column(name = "UPDATE_DATE", nullable = false)
    private Date updatedDate;

    @ManyToOne
    private Blogger blogger;

    @ManyToMany
    @JoinTable(name = "ARTICLE_TAG",
            joinColumns = {@JoinColumn(name = "ARTICLE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TAG_ID")})
    private List<Tag>  tagList;

}
