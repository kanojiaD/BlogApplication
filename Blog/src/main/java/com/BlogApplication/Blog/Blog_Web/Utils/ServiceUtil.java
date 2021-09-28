package com.BlogApplication.Blog.Blog_Web.Utils;

import com.BlogApplication.Blog.Blog_Security.Services.CustomUserDetailsService;
import com.BlogApplication.Blog.Blog_Web.Exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Component
public class ServiceUtil {
    @GeneratedValue(strategy = GenerationType.AUTO)
    String str;
    @Autowired
    SequenceGenerator sequenceGenerator;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    public void authenticateUserSameAsLogedInUser(String Email, String message)
    {
        if(!customUserDetailsService.currentLogedInUserName().equals(Email))
            throw new CustomException(message);
    }

    public String generateSlug(String title, String id)
    {
        return getWellFormedTitle(title.trim())+"-"+id;
    }

    private static String getWellFormedTitle(String title) {
        StringBuffer sb= new StringBuffer();
        int cnt=title.length();
        for(int i=0; i<cnt; i++)
        {
            if(title.charAt(i)==' ' && sb.charAt(sb.length()-1)!='-') sb.append('-');
            else if(title.charAt(i)!=' ') sb.append(title.charAt(i));
        }
        return sb.toString();
    }

    public String generateUniqueID()
    {
        return sequenceGenerator.uniqueID();
    }

}
