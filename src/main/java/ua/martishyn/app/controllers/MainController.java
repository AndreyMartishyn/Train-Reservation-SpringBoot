package ua.martishyn.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.martishyn.app.models.StationDTO;
import ua.martishyn.app.service.StationService;
import ua.martishyn.app.utils.constants.ControllerConstants;

import java.util.List;

@Controller
@RequestMapping(value = {"/index", "/"})
@Slf4j
public class MainController {
    private final StationService stationService;

    @Autowired
    public MainController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    public String index(Model model) {
        List<StationDTO> allStationsDto = stationService.getAllStationsDto();
        if (allStationsDto.isEmpty()) {
            model.addAttribute("noStations", true);
        } else {
            log.info("Loading stations from db. Stations quantity : {}", allStationsDto.size());
            model.addAttribute("stations", allStationsDto);
        }
        return ControllerConstants.INDEX_PAGE;
    }
}
