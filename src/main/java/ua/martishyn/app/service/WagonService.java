package ua.martishyn.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.martishyn.app.entities.Wagon;
import ua.martishyn.app.models.WagonDTO;
import ua.martishyn.app.repositories.RouteRepository;
import ua.martishyn.app.repositories.WagonRepository;
import ua.martishyn.app.utils.enums.Type;

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

    @Transactional(readOnly = true)
    public List<WagonDTO> getAllWagonsDTO() {
        return wagonRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<WagonDTO> getAllWagonsByRouteId(Integer routeId) {
        return wagonRepository.findAllWagonsByRouteId(routeId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Reduces number of free seats in wagon by 1.
     *
     * @param wagonNum - id of selected wagon in booking form
     * @return true if seats available and wagon is present,
     * otherwise - false
     */

    @Transactional
    public boolean checkForAvailablePlaceAndBook(Integer wagonNum) {
        Wagon wagonToBeUpdated
                = wagonRepository.getById(wagonNum);
        boolean isUpdated = false;
        if (wagonToBeUpdated.getNumOfSeats() > 0) {
            int numOfFreePlaces = wagonToBeUpdated.getNumOfSeats();
            wagonToBeUpdated.setNumOfSeats(numOfFreePlaces - 1);
            wagonRepository.save(wagonToBeUpdated);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * Increase number of free seats in wagon by 1.
     *
     * @param wagonNum - id of wagon in ticket
     */
    @Transactional
    public void replenishPlaceAfterCancelling(Integer wagonNum) {
        Wagon wagonToBeUpdated
                = wagonRepository.getById(wagonNum);
        wagonToBeUpdated.setNumOfSeats(wagonToBeUpdated.getNumOfSeats() + 1);
        wagonRepository.save(wagonToBeUpdated);
    }

    /**
     * Gets number of places for certain wagon type
     *
     * @param wagons - list of collected wagons from route
     * @param type   - enum type of wagon
     * @return value summed through streaming list or 0 if no wagons
     * for certain type
     */
    public int getClassPlaces(List<WagonDTO> wagons, Type type) {
        return wagons.stream()
                .filter(wagon -> wagon.getType().name().equals(type.name()))
                .mapToInt(WagonDTO::getNumOfSeats)
                .reduce(0, Integer::sum);
    }

    /**
     * Search list with wagons for the first occurrence of requested wagon type.
     * Calculates price based on wagon type price and number of stations.
     * Note, that wagons with same class has same base price.
     *
     * @param wagons       - list of collected wagons from route
     * @param type         - enum type
     * @param numOfStation - number of stations value
     * @return calculated price or 0, if wagon not found
     */
    public int getPriceForClassSeat(List<WagonDTO> wagons, Type type, int numOfStation) {
        return wagons.stream()
                .filter(wagonDTO -> wagonDTO.getType().equals(type))
                .findFirst()
                .map(wagonDTO -> wagonDTO.getBasePrice() * numOfStation).orElse(0);
    }

    @Transactional(readOnly = true)
    public WagonDTO getWagonDtoById(Integer id) {
        return convertToDto(wagonRepository.getById(id));
    }

    @Transactional
    public void createOrUpdateWagon(WagonDTO wagonDTO) {
        Wagon wagonToSave = convertDtoToEntity(wagonDTO);
        wagonRepository.save(wagonToSave);
    }

    @Transactional
    public void deleteWagon(Integer wagonId) {
        wagonRepository.deleteById(wagonId);
    }

    public WagonDTO convertToDto(Wagon wagon) {
        return modelMapper.map(wagon, WagonDTO.class);
    }

    public Wagon convertDtoToEntity(WagonDTO wagonDTO) {
        Wagon wagonEntity = modelMapper.map(wagonDTO, Wagon.class);
        wagonEntity.setRoute(routeRepository.findById(wagonDTO.getRoute()).get());
        return wagonEntity;
    }

}
