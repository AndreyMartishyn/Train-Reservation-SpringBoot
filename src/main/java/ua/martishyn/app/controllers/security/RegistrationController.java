package ua.martishyn.app.controllers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.martishyn.app.models.UserRegisterDTO;
import ua.martishyn.app.service.UserService;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(@ModelAttribute("user") UserRegisterDTO userRegisterDTO) {
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserRegisterDTO userRegisterDto,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        boolean isRegistered = userService.registerUser(userRegisterDto);
        if (!isRegistered) {
            model.addAttribute("registrationError", true);
            return "registration";
        }
        return "redirect:/index";
    }


}
