package ua.martishyn.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.martishyn.app.entities.Wagon;

public interface WagonRepository extends JpaRepository<Wagon, Integer> {
}
