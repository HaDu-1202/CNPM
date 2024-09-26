package com.cnpm.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;

@Controller
public class ForgotPasswordController {

    @GetMapping("/forgot_password")
    public ModelAndView showForgotPasswordPage() {
        // Trả về view có tên forgot_password (file HTML)
        return new ModelAndView("forgot_password");
    }

    @Autowired
    private JavaMailSender mailSender; // Để gửi email

    @PostMapping("/reset_password")
    public ModelAndView resetPassword(@RequestParam("email") String email) {
        // Sinh ngẫu nhiên một mã 5 chữ số
        String resetCode = generateResetCode();

        try {
            // Gửi mã reset password tới email của người dùng
            sendResetEmail(email, resetCode);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Nếu có lỗi xảy ra khi gửi email, trả về thông báo lỗi
            ModelAndView mav = new ModelAndView("forgot_password");
            mav.addObject("error", "There was an error sending the reset code. Please try again.");
            return mav;
        }

        // Chuyển hướng đến trang nhập mã reset
        ModelAndView mav = new ModelAndView("redirect:/check_email");
        mav.addObject("email", email);
        return mav;
    }

    // Hàm sinh mã 5 chữ số ngẫu nhiên
    private String generateResetCode() {
        Random random = new Random();
        int randomCode = 10000 + random.nextInt(90000); // Sinh số ngẫu nhiên từ 10000 đến 99999
        return String.valueOf(randomCode);
    }

    // Hàm gửi email chứa mã reset password
    private void sendResetEmail(String email, String resetCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Reset your password");
        helper.setText("Your password reset code is: " + resetCode, true);
        mailSender.send(message);
    }
}
