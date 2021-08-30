package com.BlogApplication.Blog.Blog_Security.Services;

import com.BlogApplication.Blog.Blog_Web.Entity.Blogger;
import com.BlogApplication.Blog.Blog_Web.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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
        Blogger blogger= userRepository.findBloggerByDomain(domain);
        try
        {
            String username= blogger.getEmail();
            String password= blogger.getPassword();
            return new User( username, password, new ArrayList<>());
        }
        catch(Exception e)
        {
            throw new UsernameNotFoundException("User Not Found!!");
        }
    }
}
