package com.BlogApplication.Blog.Blog_Security.SecurityConfig;

import com.BlogApplication.Blog.Blog_Security.Helper.JwtUtil;
import com.BlogApplication.Blog.Blog_Security.Services.CustomUserDetailsService;
import com.BlogApplication.Blog.Blog_Web.Exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");
        String username= null;
        String jwtToken= null;
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer "))
        {
            jwtToken= requestTokenHeader.substring(7);
            try{
                username = this.jwtUtil.extractUsername(jwtToken);
            }
            catch (Exception e)
            {
                throw new CustomException("User not found!!");
            }
            UserDetails userDetails= this.customUserDetailsService.loadUserByUsername(username);
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else
            {
                throw new UsernameNotFoundException("User name not valid");
            }
        }
        filterChain.doFilter(request, response);
    }
}

