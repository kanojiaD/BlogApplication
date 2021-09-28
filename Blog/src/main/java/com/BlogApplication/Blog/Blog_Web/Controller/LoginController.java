package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Security.Helper.JwtUtil;
import com.BlogApplication.Blog.Blog_Security.Model.JwtRequest;
import com.BlogApplication.Blog.Blog_Security.Model.JwtResponse;
import com.BlogApplication.Blog.Blog_Security.Services.CustomUserDetailsService;
import com.BlogApplication.Blog.Blog_Web.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Login API.
     * @RequestBody : JwtRequest
     * @return : JwtResponse(Token).
     */
    @PostMapping("/blog/signin/")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest)
    {
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        }
        catch(Exception e)
        {
            throw new UserNotFoundException("Invalid Email or Password");
        }
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
