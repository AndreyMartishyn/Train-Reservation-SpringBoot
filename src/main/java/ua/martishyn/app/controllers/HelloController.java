package ua.martishyn.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/main")
    public String index(){
        return "hello";
    }
}
