package com.BlogApplication.Blog.Blog_Web.CustomAnnotation;


import com.BlogApplication.Blog.Blog_Web.CustomValidator.SpecialCharacterValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SpecialCharacterValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotSpecialCharacter {
    public String message() default "must not contain special character";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
