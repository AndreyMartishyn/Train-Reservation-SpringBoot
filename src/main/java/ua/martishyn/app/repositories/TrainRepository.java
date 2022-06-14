package ua.martishyn.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.martishyn.app.entities.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train, Integer> {
}
