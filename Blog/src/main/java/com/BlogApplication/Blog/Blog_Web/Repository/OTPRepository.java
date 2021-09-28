package com.BlogApplication.Blog.Blog_Web.Repository;

import com.BlogApplication.Blog.Blog_Web.Entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<OTP, String> {
}
