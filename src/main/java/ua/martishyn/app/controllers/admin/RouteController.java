package ua.martishyn.app.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.martishyn.app.entities.Train;
import ua.martishyn.app.models.route.RouteDTO;
import ua.martishyn.app.models.route.RoutePointDTO;
import ua.martishyn.app.service.RouteService;
import ua.martishyn.app.service.StationService;
import ua.martishyn.app.service.TrainService;
import ua.martishyn.app.utils.constants.ControllerConstants;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping()
@Slf4j
public class RouteController {
    private final RouteService routeService;
    private final StationService stationService;
    private final TrainService trainService;

    @Autowired
    public RouteController(RouteService routeService,
                           StationService stationService,
                           TrainService trainService) {
        this.routeService = routeService;
        this.stationService = stationService;
        this.trainService = trainService;
    }

    @GetMapping("/admin/routes")
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


    @GetMapping("/booking/track")
    public String showFoundRouteSchedule(@RequestParam("routeId") Integer routeId,
                                         @RequestParam("firstStation") Integer stationFromId,
                                         @RequestParam("lastStation") Integer stationToId,
                                         Model model){
        model.addAttribute("desiredRoute", routeService.collectRoutePointsForView(routeId, stationFromId, stationToId));
        return "/common/show_route_schedule";
    }

    @GetMapping("/admin/routes/add-new-route")
    public String showAddNewRouteForm(@ModelAttribute("newRoute") RouteDTO routeDTO,
                                      Model model) {
        model.addAttribute("trains",trainService.getAllTrains());
        return ControllerConstants.ROUTE_ADD_PAGE;
    }

    @PostMapping("/admin/routes/add-route")
    public String addNewRoute(@ModelAttribute("newRoute") @Valid RouteDTO routeDTO,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ControllerConstants.ROUTE_ADD_PAGE;
        }
        routeService.addRouteWithoutStations(routeDTO);
        return ControllerConstants.ROUTE_REDIRECT;
    }

    @PostMapping("/admin/routes/{routeId}/delete")
    public String addNewRoute(@PathVariable("routeId") Integer routeId) {
        routeService.deleteRoute(routeId);
        return ControllerConstants.ROUTE_REDIRECT;

    }

    @GetMapping("/admin/routes/{routeId}/show-add-form")
    public String showRoutePointEditForm(@PathVariable("routeId") Integer routeId,
                                         @ModelAttribute("newPoint") RoutePointDTO routePointDTO,
                                         Model model) {

        routePointDTO.setRouteId(routeId);
        model.addAttribute("stationList", stationService.getAllStationsDto());
        return ControllerConstants.ROUTE_POINT_ADD_PAGE;
    }

    @GetMapping("/admin/routes/{routePointId}/edit/show-edit-form")
    public String showRoutePointEditForm(@PathVariable("routePointId") Integer routePointId,
                                         Model model) {
        model.addAttribute("routePoint", routeService.getRoutePointDtoById(routePointId));
        model.addAttribute("stationList", stationService.getAllStationsDto());
        return ControllerConstants.ROUTE_POINT_EDIT_PAGE;
    }

    @PostMapping("/admin/routes/add-point")
    public String addRoutePoint(@ModelAttribute("newPoint") @Valid RoutePointDTO routePointDTO,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ControllerConstants.ROUTE_POINT_ADD_PAGE;
        }
        routeService.addOrUpdateRoutePointToExistingRoute(routePointDTO);
        return ControllerConstants.ROUTE_REDIRECT;
    }

    @PostMapping("/admin/routes/edit-point")
    public String updateRoutePoint(@ModelAttribute("routePoint") @Valid RoutePointDTO routePointDTO,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ControllerConstants.ROUTE_POINT_EDIT_PAGE;
        }
        routeService.addOrUpdateRoutePointToExistingRoute(routePointDTO);
        return ControllerConstants.ROUTE_REDIRECT;
    }

    @PostMapping("/admin/routes/delete/{routePointId}")
    public String addRoutePoint(@PathVariable("routePointId") Integer routePointId) {
        routeService.deleteRoutePoint(routePointId);
        return ControllerConstants.ROUTE_REDIRECT;
    }


}
