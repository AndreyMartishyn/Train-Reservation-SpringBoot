package ua.martishyn.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.martishyn.app.models.StationDTO;
import ua.martishyn.app.models.UserDTO;
import ua.martishyn.app.service.StationService;

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
        return "/admin/station_list";
    }

    @GetMapping("/add")
        public String showAddForm(@ModelAttribute("station") StationDTO stationDTO){
            return "/admin/station_add";
    }

    @PostMapping("/add")
    public String addStation(@ModelAttribute("station") StationDTO stationDTO){
        stationService.addStation(stationDTO);
        return "redirect:/admin/stations";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        try {
            StationDTO stationDTO = stationService.getUserDtoByEntityId(id);
            model.addAttribute("station", stationDTO);
        } catch (Exception e) {
            log.error("Station not found with id {}", id);
            e.printStackTrace();
            return "redirect:/admin/stations";
        }
        return "/admin/station_edit";
    }

    @PostMapping("/{id}/edit")
    public String updateStation(@ModelAttribute("station") @Valid StationDTO stationDTO,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/admin/station_edit";
        }
            stationService.updateStationFromDtoData(stationDTO);
        return "redirect:/admin/stations";
    }

    @PostMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") int id){
        try {
            stationService.deleteUserById(id);
        } catch (Exception e) {
            log.error("Station with id {} not deleted", id);
            e.printStackTrace();
        }
        return "redirect:/admin/stations";
    }
}
