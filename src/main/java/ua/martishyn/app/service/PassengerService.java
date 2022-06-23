package ua.martishyn.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.martishyn.app.entities.PassengerDetails;
import ua.martishyn.app.repositories.PassengerRepository;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Transactional
    public PassengerDetails saveNewPassenger(PassengerDetails passengerDetails){
        return passengerRepository.save(passengerDetails);
    }
}
