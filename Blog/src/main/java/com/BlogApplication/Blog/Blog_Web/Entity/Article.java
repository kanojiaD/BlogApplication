package com.BlogApplication.Blog.Blog_Web.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ARTICLES")
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long articleId;

    private String postId;

    @Column(name = "TITLE")
    @NotNull(message = "Title should not be null")
    private String title;

    @Column(name="CONTENT")
    private String content;

    @Column(name = "PUBLISHED_ON", nullable = false)
    private Timestamp publishedOn;

    @Column(name = "UPDATED_ON", nullable = false)
    private Timestamp updatedOn;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<Terms> terms=new HashSet<>();
    public void addTerms(Terms term)
    {
        terms.add(term);
    }

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Tag> tags= new ArrayList<>();

    public void addTagInArticle(Tag tag)
    {
        this.tags.add(tag);
    }

    public void removeTagFromArticle(Tag tag)
    {
        this.tags.remove(tag);
    }
}
