package ua.martishyn.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.martishyn.app.entities.Train;
import ua.martishyn.app.repositories.TrainRepository;

import java.util.List;

@Service
public class TrainService {
    private final TrainRepository trainRepository;

    @Autowired
    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public Train getTrainById(Integer id){
        return trainRepository.getById(id);
    }

    public List<Train> getAllTrains(){
        return trainRepository.findAll();
    }
}
