package com.BlogApplication.Blog.Blog_Web.Repository;

import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

}
