package ua.martishyn.app.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.martishyn.app.models.WagonDTO;
import ua.martishyn.app.service.RouteService;
import ua.martishyn.app.service.WagonService;
import ua.martishyn.app.utils.constants.ControllerConstants;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/wagons")
public class WagonController {
    private final WagonService wagonService;
    private final RouteService routeService;

    @Autowired
    public WagonController(WagonService wagonService,
                           RouteService routeService) {
        this.wagonService = wagonService;
        this.routeService = routeService;
    }

    @GetMapping
    public String getAllWagons(Model model) {
        List<WagonDTO> wagons = wagonService.getAllWagonsDTO();
        if (wagons.isEmpty()) {
            model.addAttribute("noWagons", true);
        } else {
            model.addAttribute("wagonsList", wagons);
        }
        return ControllerConstants.WAGON_LIST;
    }

    @GetMapping("/add-form")
    public String showAddWagonForm(@ModelAttribute("newWagon") WagonDTO wagonDTO,
                                   Model model) {
        model.addAttribute("routeIds", routeService.getAllNotEmptyRoutesIds());
        return ControllerConstants.WAGON_ADD_PAGE;
    }

    @PostMapping("/add")
    public String addNewWagon(@ModelAttribute("newWagon") @Valid WagonDTO wagonDTO,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("routeIds", routeService.getAllNotEmptyRoutesIds());
            return ControllerConstants.WAGON_ADD_PAGE;
        }
        wagonService.createOrUpdateWagon(wagonDTO);
        return ControllerConstants.WAGON_REDIRECT;
    }

    @GetMapping("/{id}/edit-form")
    public String showWagonEditForm(@PathVariable("id") Integer id,
                                    Model model) {
        model.addAttribute("wagon", wagonService.getWagonDtoById(id));
        return ControllerConstants.WAGON_EDIT_PAGE;
    }

    @PostMapping("/{id}/edit")
    public String updateExistingWagon(@ModelAttribute("wagon") @Valid WagonDTO wagonDTO,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ControllerConstants.WAGON_EDIT_PAGE;
        }
        wagonService.createOrUpdateWagon(wagonDTO);
        return ControllerConstants.WAGON_REDIRECT;
    }

    @PostMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") int id) {
        wagonService.deleteWagon(id);
        return ControllerConstants.WAGON_REDIRECT;
    }
}
