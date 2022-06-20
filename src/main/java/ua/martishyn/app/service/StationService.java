package ua.martishyn.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public List<StationDTO> getAllStationsDtoPaginated(Integer pageNum,
                                                       Integer pageSize) {
        return stationRepository.findAll(PageRequest.of(pageNum, pageSize))
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public long getRowsCountForStations(){
        return stationRepository.count();
    }

    public StationDTO getStationDtoById(int id) {
        Optional<Station> stationFromDb = stationRepository.findStationById(id);
        return convertToDto(stationFromDb.get());
    }

    public void updateStationFromDtoData(StationDTO stationDTO) {
        Station stationEntity = convertToEntity(stationDTO);
        stationRepository.save(stationEntity);
    }

    public boolean deleteStationById(int id) {
        Optional<Station> stationFromDb = stationRepository.findStationById(id);
        if (!stationFromDb.isPresent()) {
            return false;
        }
        stationRepository.delete(stationFromDb.get());
        return true;
    }

    public StationDTO convertToDto(Station station) {
        return modelMapper.map(station, StationDTO.class);
    }

    public Station convertToEntity(StationDTO stationDTO) {
        return modelMapper.map(stationDTO, Station.class);
    }
}
