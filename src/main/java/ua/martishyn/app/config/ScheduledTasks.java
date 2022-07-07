package ua.martishyn.app.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.martishyn.app.entities.Ticket;
import ua.martishyn.app.service.TicketService;
import ua.martishyn.app.utils.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@AllArgsConstructor
public class ScheduledTasks {
    private final TicketService ticketService;

    @Scheduled(initialDelay = 1,fixedDelay = 5,timeUnit = TimeUnit.MINUTES)
    public void deleteNotPayedExpiredTicket() {
        log.info("Task started at:{}", LocalDateTime.now());
        List<Ticket> allTicketsFromBase = ticketService.getAllTickets();
        for (Ticket t :allTicketsFromBase){
            if (t.getTicketDetails().getStatus().equals(TicketStatus.NOT_PAID) &&
                checkIfExpired(t.getCreatedAt())) {
                ticketService.cancelBooking(t.getId(), t.getUser());
            }
        }
        log.info("Task has ended at:{}", LocalDateTime.now());
    }

    private boolean checkIfExpired(LocalDateTime localDateTime) {
        return localDateTime.plusMinutes(15).isBefore(LocalDateTime.now());
    }
}
