package com.cnpm.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/home_page")
    public ModelAndView homePage() {
        return new ModelAndView("home_page"); // Trả về view home_page.html
    }
}
