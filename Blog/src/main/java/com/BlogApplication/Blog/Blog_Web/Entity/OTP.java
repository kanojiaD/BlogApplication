package com.BlogApplication.Blog.Blog_Web.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OTP {
    @Id
    @Column(name = "EMAIL", unique = true)
    private String email;

    private String otp;


}
