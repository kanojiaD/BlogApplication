package com.BlogApplication.Blog.Blog_Web.Services;

import com.BlogApplication.Blog.Blog_Security.Services.CustomUserDetailsService;
import com.BlogApplication.Blog.Blog_Web.Entity.OTP;
import com.BlogApplication.Blog.Blog_Web.Entity.Users;
import com.BlogApplication.Blog.Blog_Web.Exceptions.CustomException;
import com.BlogApplication.Blog.Blog_Web.Exceptions.UserNotFoundException;
import com.BlogApplication.Blog.Blog_Web.Message.CustomMessage;
import com.BlogApplication.Blog.Blog_Web.Repository.OTPRepository;
import com.BlogApplication.Blog.Blog_Web.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    OTPRepository otpRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    MailService mailService;

    public CustomMessage getOtp(String email) {
        if(userRepository.findUserByEmail(email)==null) throw new UserNotFoundException();
        String otp = (Math.random()+"").substring(2,8);
        System.out.println(otp);
        mailService.sendMail(email, otp);
        OTP otpObject= new OTP();
        try {
            otpObject = otpRepository.findById(email).get();
        }
        catch (Exception e)
        {
            otpObject.setEmail(email);
        }
        otpObject.setOtp(bCryptPasswordEncoder.encode(otp));
        otpRepository.save(otpObject);
        return new CustomMessage("OTP has been send to the mail "+ email);
    }

    public CustomMessage setPassword(String email,String new_password, String confirm_password,  String otp) {
        try {
            if (bCryptPasswordEncoder.matches(otp, otpRepository.getById(email).getOtp())) {
                if (!new_password.equals(confirm_password))
                    return new CustomMessage("Must type the same password each time");
                Users user = userRepository.findUserByEmail(email);
                user.setPassword(bCryptPasswordEncoder.encode(new_password));
                otpRepository.delete(otpRepository.getById(email));
                return  new CustomMessage("Password has been reset successfully!!");
            }
            else return new CustomMessage("incorrect OTP");
        }
        catch(Exception e)
        {
            throw new CustomException("incorrect OTP");
        }
    }

    public CustomMessage changePassword(String current_password, String new_password, String confirm_password) {
        Users user= userRepository.findUserByEmail(customUserDetailsService.currentLogedInUserName());
        if(bCryptPasswordEncoder.matches(current_password, user.getPassword())) {
            if(new_password.equals(confirm_password)) user.setPassword(bCryptPasswordEncoder.encode(new_password));
            else return new CustomMessage("Must type the same password each time");
        }
        else return new CustomMessage("incorrect password");
        return new CustomMessage("Password has been reset successfully!!");
    }
}
