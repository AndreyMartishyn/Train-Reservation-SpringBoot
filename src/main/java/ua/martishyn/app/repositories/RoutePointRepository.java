package ua.martishyn.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.martishyn.app.entities.RoutePoint;

@Repository
public interface RoutePointRepository extends JpaRepository<RoutePoint, Integer>, JpaSpecificationExecutor<RoutePoint> {
    RoutePoint getById(Integer id);

}
