package ua.martishyn.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.martishyn.app.models.PersonalRoute;
import ua.martishyn.app.service.RouteFindHelper;
import ua.martishyn.app.utils.constants.ControllerConstants;

import java.util.List;

@Controller
@RequestMapping("/booking")
@Slf4j
public class BookingController {
    private final RouteFindHelper routeFindHelper;

    @Autowired
    public BookingController(RouteFindHelper routeFindHelper) {
        this.routeFindHelper = routeFindHelper;
    }

    @GetMapping
    public String findTickets(@RequestParam("stationFrom") int fromId,
                              @RequestParam("stationTo") int toId,
                              Model model,
                              RedirectAttributes attr) {
        if (routeFindHelper.areStationsSame(fromId, toId)) {
            attr.addFlashAttribute("sameStations", "Departure and Arrival Stations are same");
            return ControllerConstants.INDEX_PAGE_REDIRECT;
        } else {
            routeFindHelper.makeBooking(fromId, toId);
            List<PersonalRoute> suitableRoutes = routeFindHelper.getSuitableRoutes();
            if (!suitableRoutes.isEmpty()) {
                log.info("Appropriate routes found. Size : {}", suitableRoutes.size());
                model.addAttribute("suitableRoutes", suitableRoutes);
            }
        }
        return ControllerConstants.INDEX_PAGE;
    }
}
