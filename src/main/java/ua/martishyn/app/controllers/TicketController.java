package ua.martishyn.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.martishyn.app.config.security.UserPrincipal;
import ua.martishyn.app.service.TicketService;

@Controller
@RequestMapping("tickets")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/show")
    public String getTicketsPageForUser(@AuthenticationPrincipal UserPrincipal currentUser,
                                        Model model) {
        model.addAttribute("tickets", ticketService.getAllUsersTickets(currentUser.getUser()));
        return "/customer/my_tickets";
    }
}
