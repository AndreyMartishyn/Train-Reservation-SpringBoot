package ua.martishyn.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.martishyn.app.entities.RoutePoint;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<RoutePoint, Integer> {
    List<RoutePoint> findAll();
}
