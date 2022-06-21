package ua.martishyn.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.martishyn.app.config.security.UserPrincipal;
import ua.martishyn.app.service.TicketService;

@Controller
@RequestMapping("/tickets/show")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String getTicketsPageForUser(@AuthenticationPrincipal UserPrincipal currentUser,
                                        Model model) {
        model.addAttribute("tickets", ticketService.getAllUsersTickets(currentUser.getUser()));
        return "/customer/my_tickets";
    }

    @PostMapping("/pay/{id}")
    public String payForUserTicket(@PathVariable("id") Integer id,
                                   @AuthenticationPrincipal UserPrincipal currentUser) {
        ticketService.payForTicket(id, currentUser.getUser());
        return "redirect:/tickets/show";
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable("id") Integer id,
                                @AuthenticationPrincipal UserPrincipal currentUser) {
        ticketService.cancelBooking(id, currentUser.getUser());
        return "redirect:/tickets/show";
    }
}
