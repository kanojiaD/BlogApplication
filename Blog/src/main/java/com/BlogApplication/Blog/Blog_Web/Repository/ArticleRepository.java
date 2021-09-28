package com.BlogApplication.Blog.Blog_Web.Repository;

import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query(value = "from Article where postId=?1")
    Article findByPostID(String postId);

    @Query(value = "SELECT a FROM Article a")
    Page<Article> findPaginatedArticle(Pageable pageable);

}