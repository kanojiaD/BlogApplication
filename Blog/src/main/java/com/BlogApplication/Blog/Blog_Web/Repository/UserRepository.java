package com.BlogApplication.Blog.Blog_Web.Repository;

import com.BlogApplication.Blog.Blog_Web.Entity.Article;
import com.BlogApplication.Blog.Blog_Web.Entity.Blogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Blogger, Long> {

    @Query(value = "from Blogger where email=?1")
    Blogger findBloggerByDomain(String mail);

//    @Query(value = "select a FROM Blogger b JOIN b.Article a JOIN a.Tag t where (:tagname is null or t.tagname=:tagname) and (:username is null or b.username=:username) and (:title is null or a.title=:title)", nativeQuery = true)
//    List<Object> searchArticle(@Param("tagname") String tagname,
//                                @Param("username") String username,
//                                @Param("title") String title);
}
