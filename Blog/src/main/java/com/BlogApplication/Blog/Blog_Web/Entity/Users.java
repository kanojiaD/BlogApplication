package com.BlogApplication.Blog.Blog_Web.Entity;

import com.BlogApplication.Blog.Blog_Web.CustomAnnotation.NotSpecialCharacter;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "Users")
@Entity
public class Users {

    @Column(name = "USER_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userid;

    @Column(name = "USER_UUID")
    private String userUUID;

    @Column(name = "NAME")
    @NotNull(message = "User name should not be null")
    @Size(min = 3, max = 20, message = "Length of name should be greater than 3 and less than 20")
    private String name;

    @Column(name = "EMAIL_ID", unique = true, updatable = false)
    @NotNull(message = "Email should not be null")
    @NotSpecialCharacter(message = "Email must not contains special character")
    @Email
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    @NotNull(message = "Password should not be null")
    @Size(min=8, message = "Length of password should be at least 8")
    private String password;

    @Column(name = "CONTACT_NUMBER")
    @Pattern(regexp = "(0)?(\\+91)?[6-9]\\d{9}", message = "Invalid Contact Number")
    private String contact;

    @Column(name = "ROLE")
    private String role;

    public Users(String name, String email, String password, String contact) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact = contact;
    }

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Article> articles= new ArrayList<>();

    public void addArticleInBloggerList(Article article)
    {
        this.articles.add(article);
    }

    public void removeArticleFromBloggerList(Article article)
    {
        this.articles.remove(article);
    }

}
