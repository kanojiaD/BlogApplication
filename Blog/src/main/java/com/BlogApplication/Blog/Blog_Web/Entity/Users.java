package com.BlogApplication.Blog.Blog_Web.Entity;

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
    private UUID userUUID;

    @Column(name = "NAME")
    @NotNull(message = "User name should not be null")
    @Size(min = 3, max = 20, message = "Length of name should be greater than 3 and less than 20")
    private String name;

    @Column(name = "EMAIL_ID", unique = true)
    @NotNull(message = "Email should not be null")
    @Email
    private String email;

    @Column(name = "PASSWORD")
    @NotNull(message = "Password should not be null")
    @Size(min=8, message = "Length of password should be greater than or equal to 8")
    private String password;

    @Column(name = "CONTACT_NUMBER")
    @Pattern(regexp = "(0)?(\\+91)?[6-9]\\d{9}", message = "Invalid Contact Number")
    private String contact;

    @Column(name = "ROLE")
    private String role;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Article> listOfArticle= new ArrayList<>();

    public void addArticleInBloggerList(Article article)
    {
        this.listOfArticle.add(article);
    }

    public void removeArticleFromBloggerList(Article article)
    {
        this.listOfArticle.remove(article);
    }

}
