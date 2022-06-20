package ua.martishyn.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.martishyn.app.entities.Station;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {
    Optional<Station> findStationById(int id);

    List<Station> findAll();

}
