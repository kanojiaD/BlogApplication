package com.BlogApplication.Blog.Blog_Security.Services;

import com.BlogApplication.Blog.Blog_Web.Entity.Users;
import com.BlogApplication.Blog.Blog_Web.ExceptionHandling.CustomException;
import com.BlogApplication.Blog.Blog_Web.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String domain) throws UsernameNotFoundException {
        Users users = userRepository.findUserByEmail(domain);
        try
        {
            String username= users.getEmail();
            String password= users.getPassword();
            return new org.springframework.security.core.userdetails.User( username, password, new ArrayList<>());
        }
        catch(Exception e)
        {
            throw new UsernameNotFoundException("User Not Registered!!");
        }
    }


    public String currentLogedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentAuthenticateEmail = authentication.getName();
        if(currentAuthenticateEmail.equals("anonymousUser")) throw new CustomException("Please Login!!");
        return currentAuthenticateEmail;
    }
}
