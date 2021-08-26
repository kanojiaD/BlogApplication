package com.BlogApplication.Blog.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "USERS")
@Entity
public class User {
    @Column(name = "USER_ID")
    @Id
    private UUID userId;

    @Column(name = "USER_NAME", nullable = false)
    @Size(min = 3, max = 20, message = "Length of name should be greater than 3 and less than 20")
    private String userName;

    @Column(name = "EMAIL_ID", nullable = false)
    @Email(message = "Wrong email address")
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    @Size(min=8, message = "Length of password should be greater than or equal to 8")
    private String userPassword;

    @Column(name = "CONTACT_NUMBER")
    @Size(min = 10, max = 10, message = "wrong contact number")
    /**
     * I have to add a regular ecpression in place of @Size(...)
     */
    private String contact;

    @Column(name = "ROLE")
    @NotNull(message = "Role should not be null")
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Article> userArticleList;

}
