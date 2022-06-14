package ua.martishyn.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.martishyn.app.entities.Wagon;
import ua.martishyn.app.models.WagonDTO;
import ua.martishyn.app.repositories.RouteRepository;
import ua.martishyn.app.repositories.WagonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WagonService {
    private final WagonRepository wagonRepository;
    private final RouteRepository routeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public WagonService(WagonRepository wagonRepository,
                        RouteRepository routeRepository,
                        ModelMapper modelMapper) {
        this.wagonRepository = wagonRepository;
        this.routeRepository = routeRepository;
        this.modelMapper = modelMapper;
    }

    public List<WagonDTO> getAllWagonsDTO() {
        return wagonRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public WagonDTO getWagonDtoById(Integer id) {
        return convertToDto(wagonRepository.getById(id));
    }

    public void createOrUpdateWagon(WagonDTO wagonDTO) {
        Wagon wagonToSave = convertDtoToEntity(wagonDTO);
        wagonRepository.save(wagonToSave);
    }

    public void deleteWagon(Integer wagonId) {
        wagonRepository.deleteById(wagonId);
    }

    private WagonDTO convertToDto(Wagon wagon) {
        return modelMapper.map(wagon, WagonDTO.class);
    }

    private Wagon convertDtoToEntity(WagonDTO wagonDTO) {
        Wagon wagonEntity = modelMapper.map(wagonDTO, Wagon.class);
        wagonEntity.setRoute(routeRepository.findById(wagonDTO.getRoute()).get());
        return wagonEntity;    }
}
