package com.example.navigator.demos.web.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultController {

    @GetMapping("/result")
    public String resultPage() {
        return "result"; // 返回 result.html 页面
    }
}
