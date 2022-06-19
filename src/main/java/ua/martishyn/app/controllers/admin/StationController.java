package ua.martishyn.app.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.martishyn.app.models.StationDTO;
import ua.martishyn.app.service.StationService;
import ua.martishyn.app.utils.constants.ControllerConstants;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/stations")
@Slf4j
public class StationController {
    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    public String getAllStations(Model model) {
        List<StationDTO> allStationsDto = stationService.getAllStationsDto();
        if (allStationsDto.isEmpty()) {
            model.addAttribute("noStations", true);
        } else {
            model.addAttribute("stationsDto", allStationsDto);
        }
        return ControllerConstants.STATION_LIST;
    }

    @GetMapping("/add")
    public String showAddForm(@ModelAttribute("station") StationDTO stationDTO) {
        return ControllerConstants.STATION_ADD_PAGE;
    }

    @PostMapping("/add-new-station")
    public String addStation(@ModelAttribute("station") @Valid StationDTO stationDTO,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ControllerConstants.STATION_ADD_PAGE;
        }
        stationService.addStation(stationDTO);
        return ControllerConstants.STATION_REDIRECT;
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        try {
            StationDTO stationDTO = stationService.getStationDtoById(id);
            model.addAttribute("station", stationDTO);
        } catch (Exception e) {
            log.error("Station not found with id {}", id);
            e.printStackTrace();
            return ControllerConstants.STATION_REDIRECT;
        }
        return ControllerConstants.STATION_EDIT_PAGE;
    }

    @PostMapping("/{id}/edit")
    public String updateStation(@ModelAttribute("station") @Valid StationDTO stationDTO,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ControllerConstants.STATION_EDIT_PAGE;
        }
        stationService.updateStationFromDtoData(stationDTO);
        return ControllerConstants.STATION_REDIRECT;
    }

    @PostMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") int id) {
        try {
            stationService.deleteStationById(id);
        } catch (Exception e) {
            log.error("Station with id {} not deleted", id);
            e.printStackTrace();
        }
        return ControllerConstants.STATION_REDIRECT;
    }
}
