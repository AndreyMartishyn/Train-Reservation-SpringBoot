package ua.martishyn.app.schedule;

import lombok.AllArgsConstructor;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ua.martishyn.app.config.ScheduledTasks;
import ua.martishyn.app.entities.Ticket;
import ua.martishyn.app.entities.TicketDetails;
import ua.martishyn.app.service.TicketService;
import ua.martishyn.app.utils.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduledTasksTest {

    @SpyBean
    private ScheduledTasks scheduledTasks;

    @Mock
    private TicketService ticketService;

    @Test
    void shouldReportStartAndEndOfTask() {
        await().atMost(Duration.ONE_MINUTE)
                .untilAsserted(() -> {
                    Mockito.verify(scheduledTasks, atLeast(1)).deleteNotPayedExpiredTicket();
                });
    }

    @Test
    void shouldNotDeleteNotExpiredTicket(){
        Ticket testTicket = new Ticket();
        List<Ticket> testList = new ArrayList<>();
        TicketDetails ticketDetails = new TicketDetails();
        ticketDetails.setStatus(TicketStatus.NOT_PAID);
        testTicket.setTicketDetails(ticketDetails);
        testTicket.setCreatedAt(LocalDateTime.now().minusMinutes(5));
        testList.add(testTicket);
        when(ticketService.getAllTickets()).thenReturn(testList);
        scheduledTasks.deleteNotPayedExpiredTicket();
    }
}
