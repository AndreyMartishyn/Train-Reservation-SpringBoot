package ua.martishyn.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.martishyn.app.entities.Station;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Integer> {
    Optional<Station> findStationById(int id);
}
