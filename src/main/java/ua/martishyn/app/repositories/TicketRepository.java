package ua.martishyn.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.martishyn.app.entities.Ticket;
import ua.martishyn.app.entities.User;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("From Ticket WHERE user=?1")
    List<Ticket> findAllByUser(User user);
}
