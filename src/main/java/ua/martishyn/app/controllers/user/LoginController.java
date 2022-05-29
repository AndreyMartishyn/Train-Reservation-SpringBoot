package ua.martishyn.app.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLoginPage(@RequestParam(value = "error", required = false)
                                       boolean error, Model model) {
        if (error) {
            model.addAttribute("loginError", true);
        }
        return "login";
    }
}
