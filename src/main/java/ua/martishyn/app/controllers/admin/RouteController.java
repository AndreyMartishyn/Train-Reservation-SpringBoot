package ua.martishyn.app.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.martishyn.app.models.RoutePointDTO;
import ua.martishyn.app.service.RouteService;

import java.util.List;

@Controller
@RequestMapping("/admin/routes")
@Slf4j
public class RouteController {
    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public String getAllRoutePoints(Model model) {
        List<RoutePointDTO> allRoutesPointsDto = routeService.getAllRoutePointDTO();
        if (allRoutesPointsDto.isEmpty()) {
            model.addAttribute("noRoutes", true);
        } else {
            model.addAttribute("routePointList", allRoutesPointsDto);
        }
        return "/admin/route_list";
    }
}
