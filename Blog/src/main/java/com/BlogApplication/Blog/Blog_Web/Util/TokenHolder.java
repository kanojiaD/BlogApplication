package com.BlogApplication.Blog.Blog_Web.Util;

public class TokenHolder {
    static String token=null;
     public static void setToken(String tokenStr)
    {
        token=tokenStr;
    }
    public static String getToken()
    {
        return token;
    }
}
