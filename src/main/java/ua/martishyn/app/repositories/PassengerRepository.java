package ua.martishyn.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.martishyn.app.entities.PassengerDetails;

public interface PassengerRepository extends JpaRepository<PassengerDetails, Integer> {
}
