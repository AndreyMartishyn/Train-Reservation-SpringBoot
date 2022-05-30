package ua.martishyn.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.martishyn.app.entities.Station;
import ua.martishyn.app.models.StationDTO;
import ua.martishyn.app.repositories.StationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationService {
    private final StationRepository stationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StationService(StationRepository stationRepository,
                          ModelMapper modelMapper) {
        this.stationRepository = stationRepository;
        this.modelMapper = modelMapper;
    }

    public void addStation(StationDTO stationDTO) {
        Station stationEntity = convertToEntity(stationDTO);
        stationRepository.save(stationEntity);
    }

    public List<StationDTO> getAllStationsDto() {
        List<Station> allStations = stationRepository.findAll();
        return allStations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public StationDTO getUserDtoByEntityId(int id) throws Exception {
        Optional<Station> stationFromDb = stationRepository.findStationById(id);
        if (!stationFromDb.isPresent()) {
            throw new Exception("User not found with such id");
        }
        return convertToDto(stationFromDb.get());
    }

    public void updateStationFromDtoData(StationDTO stationDTO) {
        Station stationEntity = convertToEntity(stationDTO);
        stationRepository.save(stationEntity);
    }

    public void deleteUserById(int id) throws Exception {
        Optional<Station> stationFromDb = stationRepository.findStationById(id);
        if (!stationFromDb.isPresent()) {
            throw new Exception("Station not found");
        }
        stationRepository.delete(stationFromDb.get());
    }

    private StationDTO convertToDto(Station station) {
        return modelMapper.map(station, StationDTO.class);
    }

    private Station convertToEntity(StationDTO stationDTO) {
        return modelMapper.map(stationDTO, Station.class);
    }


}
