package ua.martishyn.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.martishyn.app.config.security.UserPrincipal;
import ua.martishyn.app.models.UserDTO;
import ua.martishyn.app.service.UserService;
import ua.martishyn.app.utils.constants.ControllerConstants;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/about")
    public String aboutMePage(@AuthenticationPrincipal UserPrincipal principal,
                              Model model){
        model.addAttribute("currentUser", principal.getUser());
        return "/customer/user_about";
    }

    @GetMapping("/admin/all")
    public String getAllUsers(Model model) {
        List<UserDTO> dtoList = userService.getAllUsers();
        if (dtoList.isEmpty()) {
            model.addAttribute("noUsers", true);
        } else {
            model.addAttribute("userDtoList", dtoList);
        }
        return ControllerConstants.USER_LIST;
    }

    @GetMapping("/admin/{id}/edit")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        UserDTO userDto = userService.getUserDtoByEntityId(id);
        if (userDto == null) {
            log.error("User not found with id {}", id);
            return ControllerConstants.USER_REDIRECT;
        }
        model.addAttribute("user", userDto);
        return ControllerConstants.USER_EDIT_PAGE;
    }


    @PostMapping("/admin/{id}/edit")
    public String updateUser(@ModelAttribute("user") @Valid UserDTO userDTO,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ControllerConstants.USER_EDIT_PAGE;
        }
        boolean isUpdated = userService.updateUserFromDtoData(userDTO.getId(), userDTO);
        if (!isUpdated) {
            log.error("User with id {} not updated", userDTO.getId());
        }
        return ControllerConstants.USER_REDIRECT;
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUserById(@PathVariable("id") int id) {
        boolean isDeleted = userService.deleteUserById(id);
        if (!isDeleted) {
            log.error("User with id {} not deleted", id);
        }
        return ControllerConstants.USER_REDIRECT;
    }
}
