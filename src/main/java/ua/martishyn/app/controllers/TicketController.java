package ua.martishyn.app.controllers;

import com.google.zxing.WriterException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.martishyn.app.config.security.UserPrincipal;
import ua.martishyn.app.entities.Ticket;
import ua.martishyn.app.service.TicketService;
import ua.martishyn.app.utils.pdf.PdfGenerator;
import ua.martishyn.app.utils.qr.QrCodeGenerator;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

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

    @GetMapping("/qr/{id}")
    public String getQrCode(@PathVariable("id") Integer id,
                            Model model) {
        String data = String.valueOf(id);
        String link = "/tickets/show/pdf/" + id;
        byte[] image = new byte[0];
        try {
            image = QrCodeGenerator.getQRCodeImage(link, 250, 250);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        // Convert Byte Array into Base64 Encode String
        String qrcode = Base64.getEncoder().encodeToString(image);
        model.addAttribute("data", data);
        model.addAttribute("qrcode", qrcode);
        return "qrcode";
    }

    @SneakyThrows
    @GetMapping("/pdf/{id}")
    public void getPdf(@PathVariable("id") Integer id,
                         HttpServletResponse response) {
        response.setContentType("application/pdf");
        Ticket boughtTicket = ticketService.getTicketByItsId(id);
        PdfGenerator pdfGenerator = new PdfGenerator(boughtTicket);
        pdfGenerator.export(response);
    }
}
