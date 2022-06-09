package ua.martishyn.app.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.martishyn.app.models.UserDTO;
import ua.martishyn.app.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDTO> dtoList = userService.getAllUsers();
        if (dtoList.isEmpty()) {
            model.addAttribute("noUsers", true);
        } else {
            model.addAttribute("userDtoList", dtoList);
        }
        return "/admin/user_list";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        try {
            UserDTO userDto = userService.getUserDtoByEntityId(id);
            model.addAttribute("user", userDto);
        } catch (Exception e) {
            log.error("User not found with id {}", id);
            e.printStackTrace();
            return "redirect:/admin/users";
        }
        return "/admin/user_edit";
    }

    @PostMapping("/{id}/edit")
    public String updateUser(@ModelAttribute("user") @Valid UserDTO userDTO,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/admin/user_edit";
        }
        try {
            userService.updateUserFromDtoData(userDTO.getId(), userDTO);
        } catch (Exception e) {
            log.error("User not updated with id {}", userDTO.getId());
            e.printStackTrace();
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") int id){
        try {
            userService.deleteUserById(id);
        } catch (Exception e) {
            log.error("User with id {} not deleted", id);
            e.printStackTrace();

        }
        return "redirect:/admin/users";
    }
}
