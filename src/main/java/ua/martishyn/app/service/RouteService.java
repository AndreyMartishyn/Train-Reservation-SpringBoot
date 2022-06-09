package ua.martishyn.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.martishyn.app.entities.RoutePoint;
import ua.martishyn.app.models.RoutePointDTO;
import ua.martishyn.app.repositories.RouteRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RouteService(RouteRepository routeRepository,
                        ModelMapper modelMapper) {
        this.routeRepository = routeRepository;
        this.modelMapper = modelMapper;
    }


    public List<RoutePointDTO> getAllRoutePointDTO() {
        List<RoutePoint> allStations = routeRepository.findAll();
        if (allStations.isEmpty()) {
            return Collections.emptyList();
        }
        return allStations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

//    public void addStation(StationDTO stationDTO) {
//        Station stationEntity = convertToEntity(stationDTO);
//        stationRepository.save(stationEntity);
//    }

//    public StationDTO getUserDtoByEntityId(int id) throws Exception {
//        Optional<Station> stationFromDb = stationRepository.findStationById(id);
//        if (!stationFromDb.isPresent()) {
//            throw new Exception("User not found with such id");
//        }
//        return convertToDto(stationFromDb.get());
//    }
//
//    public void updateStationFromDtoData(StationDTO stationDTO) {
//        Station stationEntity = convertToEntity(stationDTO);
//        stationRepository.save(stationEntity);
//    }
//
//    public void deleteUserById(int id) throws Exception {
//        Optional<Station> stationFromDb = stationRepository.findStationById(id);
//        if (!stationFromDb.isPresent()) {
//            throw new Exception("Station not found");
//        }
//        stationRepository.delete(stationFromDb.get());
//    }

    private RoutePointDTO convertToDto(RoutePoint routePoint) {
        return modelMapper.map(routePoint, RoutePointDTO.class);
    }

    private RoutePoint convertToEntity(RoutePointDTO routePointDTO) {
        return modelMapper.map(routePointDTO, RoutePoint.class);
    }


}
