package com.cnpm.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    @GetMapping("/create_account")
    public ModelAndView SignupPage() {
        return new ModelAndView("create_account"); // Hiển thị trang đăng ký
    }

    @Autowired
    private UserRepository userRepository; // Repository để tương tác với cơ sở dữ liệu

    @PostMapping("/signup")
    public ModelAndView signup(@RequestParam("full-name") String fullName,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        // Kiểm tra xem username đã tồn tại trong database chưa
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            // Username đã tồn tại
            ModelAndView mav = new ModelAndView("create_account");
            mav.addObject("error", "Username already exists!");
            return mav;
        }

        // Nếu username chưa tồn tại, chèn người dùng mới vào database
        User newUser = new User();
        newUser.setUsername(username); // Lưu username
        newUser.setPassword(password); // Lưu password
        userRepository.save(newUser);

        // Chuyển hướng tới trang chủ sau khi đăng ký thành công
        return new ModelAndView("redirect:/home_page");
    }
}
