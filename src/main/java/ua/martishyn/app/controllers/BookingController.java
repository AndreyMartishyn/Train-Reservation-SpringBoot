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
import ua.martishyn.app.service.BookingService;

import java.util.List;

@Controller
@RequestMapping("/booking")
@Slf4j
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String findTickets(@RequestParam("stationFrom") int fromId,
                              @RequestParam("stationTo") int toId,
                              Model model,
                                RedirectAttributes attr) {
        if (bookingService.areStationsSame(fromId, toId)) {
            attr.addFlashAttribute("sameStations", "Departure and Arrival Stations are same");
            return "redirect:/index";
        } else {
            bookingService.makeBooking(fromId, toId);
            List<PersonalRoute> suitableRoutes = bookingService.getSuitableRoutes();
            if (!suitableRoutes.isEmpty()) {
                log.info("Appropriate routes found. Size : {}", suitableRoutes.size());
                model.addAttribute("suitableRoutes", suitableRoutes);
            }
        }
        return "/index";
    }
}
