package ua.martishyn.app.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.martishyn.app.entities.Ticket;
import ua.martishyn.app.service.TicketService;
import ua.martishyn.app.utils.constants.ControllerConstants;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {
    private final TicketService ticketService;

    @Autowired
    public OrderController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<Ticket> orders = ticketService.getAllTickets();
        if (orders.isEmpty()) {
            model.addAttribute("noOrders", true);
        } else {
            model.addAttribute("ordersList", orders);
        }
        return ControllerConstants.ORDER_LIST;
    }
}
