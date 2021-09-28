package com.BlogApplication.Blog.Blog_Web.Controller;

import com.BlogApplication.Blog.Blog_Web.Message.CustomMessage;
import com.BlogApplication.Blog.Blog_Web.Services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ResetPassword {
    @Autowired
    PasswordService passwordService;

    @PutMapping("/b2/change-password/")
    public ResponseEntity<CustomMessage> changePassword(@RequestParam(value = "current password") String current_password,
                                                        @RequestParam(value = "new password") String new_password,
                                                        @RequestParam(value = "confirm password") String confirm_password)
    {
        return new ResponseEntity<>(passwordService.changePassword(current_password, new_password, confirm_password),HttpStatus.OK);
    }

    @GetMapping("/b1/forgot-password/")
    public ResponseEntity<CustomMessage> getOtp(@RequestParam(value = "email") String email)
    {
        return new ResponseEntity<>(passwordService.getOtp(email), HttpStatus.CREATED);
    }

    @PutMapping("/b1/forgot-password/")
    public ResponseEntity<CustomMessage> setPassword(@RequestParam(value = "email") String email,
                                                     @RequestParam(value = "otp") String otp,
                                                     @RequestParam(value = "new password") String new_password,
                                                     @RequestParam(value = "confirm password") String confirm_password
                                                     )
    {
        return new ResponseEntity<>(passwordService.setPassword(email,new_password,confirm_password, otp), HttpStatus.OK);
    }

}
