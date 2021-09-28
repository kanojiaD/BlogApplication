package com.BlogApplication.Blog.Blog_Web.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Terms {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "SLUG", unique = true, nullable = false, updatable = false)
    private String slug;

    public Terms(String slug) {
        this.slug = slug;
    }

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
}
