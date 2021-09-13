package com.BlogApplication.Blog.Blog_Web.Utils;

import com.BlogApplication.Blog.Blog_Security.Services.CustomUserDetailsService;
import com.BlogApplication.Blog.Blog_Web.ExceptionHandling.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ServiceUtil {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public void authenticateUserSameAsLogedInUser(String Email, String message)
    {
        if(!customUserDetailsService.currentLogedInUserName().equals(Email))
            throw new CustomException(message);
    }
}
