package com.cnpm.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    // Phương thức GET để hiển thị trang login
    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("login"); // trả về trang login (login.html)
    }

    // Phương thức POST để xử lý đăng nhập
    @PostMapping("/login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return new ModelAndView("redirect:/home_page");
        } else {
            // Trả về trang login với thông báo lỗi
            ModelAndView mav = new ModelAndView("login");
            mav.addObject("error", "Invalid username or password");
            return mav;
        }
    }
}
