package com.BlogApplication.Blog.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "TAGS")
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String tadId;

    @Column( name = "TAG")
    @NotNull(message = "Tag should not be null")
    private String tag;

    @ManyToMany(mappedBy = "tagList")
    private List<Article> articleList;
}
