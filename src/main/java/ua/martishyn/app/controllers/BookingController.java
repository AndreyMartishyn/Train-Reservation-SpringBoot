package ua.martishyn.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.martishyn.app.config.security.UserPrincipal;
import ua.martishyn.app.models.booking.BookingData;
import ua.martishyn.app.models.booking.MatchingRoute;
import ua.martishyn.app.service.RouteFinderService;
import ua.martishyn.app.service.TicketService;
import ua.martishyn.app.service.WagonService;
import ua.martishyn.app.utils.constants.ControllerConstants;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/booking")
@SessionAttributes("data")
@Slf4j
public class BookingController {
    private final RouteFinderService routeFindHelper;
    private final WagonService wagonService;
    private final TicketService ticketService;

    @Autowired
    public BookingController(RouteFinderService routeFindHelper,
                             WagonService wagonService,
                             TicketService ticketService) {
        this.routeFindHelper = routeFindHelper;
        this.wagonService = wagonService;
        this.ticketService = ticketService;
    }

    @ModelAttribute("data")
    public BookingData getData() {
        return new BookingData();

    }

    @GetMapping
    public String findTickets(@RequestParam("stationFrom") int fromId,
                              @RequestParam("stationTo") int toId,
                              RedirectAttributes attr,
                              Model model) {
        if (routeFindHelper.areStationsSame(fromId, toId)) {
            attr.addFlashAttribute("sameStations", true);
        } else {
            List<MatchingRoute> suitableRoutes = routeFindHelper.makeBooking(fromId, toId);
            if (!suitableRoutes.isEmpty()) {
                log.info("Appropriate routes found. Size : {}", suitableRoutes.size());
                model.addAttribute("suitableRoutes", suitableRoutes);
                return "/common/search_result";
            }
            attr.addFlashAttribute("noStations", true);
        }
        return ControllerConstants.INDEX_PAGE_REDIRECT;
    }


    /**
     * Get data from URL parameters and keeps it in the Map.
     * If parameters and field have same names - object set fields itself
     *
     * @param reqParam    Map of params from URL
     * @param bookingData @ModelAttribute, which populates by data from request
     */

    @GetMapping("/form")
    public String showDataForChosenTicket(@RequestParam Map<String, String> reqParam,
                                          @ModelAttribute("data") BookingData bookingData,
                                          Model model) {
        model.addAttribute("wagons", wagonService.getAllWagonsByRouteId(bookingData.getRoute()));
        return "/customer/booking_form.html";
    }

    @PostMapping("/form/order")
    public String showDataForChosenTicket(@ModelAttribute("data") BookingData bookingData,
                                          @AuthenticationPrincipal UserPrincipal currentUser,
                                          SessionStatus status,
                                          Model model) {
        boolean isAvailablePlace = wagonService.checkForAvailablePlaceAndBook(bookingData.getWagonNum());
        if (!isAvailablePlace) {
            model.addAttribute("noPlace", true);
            return "redirect:/booking/form";
        }
        ticketService.createTicketFromUserData(bookingData, currentUser.getUser());
        log.info("Ticket ordered successfully for Route #{}", bookingData.getRoute());
        status.setComplete();
        return ControllerConstants.INDEX_PAGE_REDIRECT;
    }

}
