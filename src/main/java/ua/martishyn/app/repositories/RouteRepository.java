package ua.martishyn.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.martishyn.app.entities.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    void deleteById(Integer routeId);
}
