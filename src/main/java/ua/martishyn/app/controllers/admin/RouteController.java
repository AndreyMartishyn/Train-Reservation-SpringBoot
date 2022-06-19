package ua.martishyn.app.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.martishyn.app.models.route.RouteDTO;
import ua.martishyn.app.models.route.RoutePointDTO;
import ua.martishyn.app.service.RouteService;
import ua.martishyn.app.service.StationService;
import ua.martishyn.app.utils.constants.ControllerConstants;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/routes")
@Slf4j
public class RouteController {
    private final RouteService routeService;
    private final StationService stationService;

    @Autowired
    public RouteController(RouteService routeService,
                           StationService stationService) {
        this.routeService = routeService;
        this.stationService = stationService;
    }

    @GetMapping
    public String getAllRoutes(Model model) {
        List<RouteDTO> routes = routeService
                .getAllRoutesDTO();
        if (routes.isEmpty()) {
            model.addAttribute("noRoutes", true);
        } else {
            model.addAttribute("routeList", routes);
        }
        return ControllerConstants.ROUTE_LIST;
    }

    @GetMapping("/add-new-route")
    public String showAddNewRouteForm(@ModelAttribute("newRoute") RouteDTO routeDTO) {
        return ControllerConstants.ROUTE_ADD_PAGE;
    }

    @PostMapping("/add-route")
    public String addNewRoute(@ModelAttribute("newRoute") @Valid RouteDTO routeDTO,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ControllerConstants.ROUTE_ADD_PAGE;
        }
        routeService.addRouteWithoutStations(routeDTO);
        return ControllerConstants.ROUTE_REDIRECT;
    }

    @PostMapping("/{routeId}/delete")
    public String addNewRoute(@PathVariable("routeId") Integer routeId) {
        routeService.deleteRoute(routeId);
        return ControllerConstants.ROUTE_REDIRECT;

    }

    @GetMapping("/{routeId}/show-add-form")
    public String showRoutePointEditForm(@PathVariable("routeId") Integer routeId,
                                         @ModelAttribute("newPoint") RoutePointDTO routePointDTO,
                                         Model model) {

        routePointDTO.setRouteId(routeId);
        model.addAttribute("stationList", stationService.getAllStationsDto());
        return ControllerConstants.ROUTE_POINT_ADD_PAGE;
    }

    @GetMapping("/{routePointId}/edit/show-edit-form")
    public String showRoutePointEditForm(@PathVariable("routePointId") Integer routePointId,
                                         Model model) {
        model.addAttribute("routePoint", routeService.getRoutePointDtoById(routePointId));
        model.addAttribute("stationList", stationService.getAllStationsDto());
        return ControllerConstants.ROUTE_POINT_EDIT_PAGE;
    }

    @PostMapping("/add-point")
    public String addRoutePoint(@ModelAttribute("newPoint") @Valid RoutePointDTO routePointDTO,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ControllerConstants.ROUTE_POINT_ADD_PAGE;
        }
        routeService.addOrUpdateRoutePointToExistingRoute(routePointDTO);
        return ControllerConstants.ROUTE_REDIRECT;
    }

    @PostMapping("/edit-point")
    public String updateRoutePoint(@ModelAttribute("routePoint") @Valid RoutePointDTO routePointDTO,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ControllerConstants.ROUTE_POINT_EDIT_PAGE;
        }
        routeService.addOrUpdateRoutePointToExistingRoute(routePointDTO);
        return ControllerConstants.ROUTE_REDIRECT;
    }

    @PostMapping("/delete/{routePointId}")
    public String addRoutePoint(@PathVariable("routePointId") Integer routePointId) {
        routeService.deleteRoutePoint(routePointId);
        return ControllerConstants.ROUTE_REDIRECT;
    }


}
