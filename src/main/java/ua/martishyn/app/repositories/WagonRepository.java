package ua.martishyn.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.martishyn.app.entities.Wagon;

import java.util.List;

public interface WagonRepository extends JpaRepository<Wagon, Integer> {

    @Query(value = "SELECT * FROM Wagon WHERE route_id=?1",
    nativeQuery = true)
    List<Wagon> findAllWagonsByRouteId(Integer id);
}
