package com.BlogApplication.Blog.Blog_Web.CustomValidator;

import com.BlogApplication.Blog.Blog_Web.CustomAnnotation.NotSpecialCharacter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SpecialCharacterValidator implements ConstraintValidator<NotSpecialCharacter, String> {
        @Override
        public void initialize(NotSpecialCharacter constraintAnnotation) {
        }

        @Override
        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            boolean result=true;
            for(int i = 0; i<s.length(); i++)
            {
                if(!(s.charAt(i)==46 ||
                        (s.charAt(i)>47 && s.charAt(i)<58) ||
                        (s.charAt(i)>63 && s.charAt(i)<91) ||
                        (s.charAt(i)>96 && s.charAt(i)<123) ||
                        s.charAt(i)==95
                ))
                {
                    result=false;
                    break;
                }

            }
            return result;
        }
    }
