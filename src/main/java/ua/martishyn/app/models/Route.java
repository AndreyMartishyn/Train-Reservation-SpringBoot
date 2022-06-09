package ua.martishyn.app.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Route {
    private int id;
    private int trainId;
    private List<IntermediateStation> intermediateStations = new ArrayList<>();

    public static class IntermediateStation implements Serializable {
        StationDTO station;
        LocalDateTime arrivalDate;
        LocalDateTime departureDate;

        public IntermediateStation(StationDTO station, LocalDateTime arrivalDate, LocalDateTime departureDate) {
            this.station = station;
            this.arrivalDate = arrivalDate;
            this.departureDate = departureDate;
        }

        public StationDTO getStation() {
            return station;
        }

        public LocalDateTime getArrivalDate() {
            return arrivalDate;
        }

        public LocalDateTime getDepartureDate() {
            return departureDate;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public void addIntermediateStation(StationDTO station, LocalDateTime arrival, LocalDateTime departure) {
        intermediateStations.add(new IntermediateStation(station, arrival, departure));
    }

    public List<IntermediateStation> getIntermediateStations() {
        return intermediateStations;
    }

    public void setIntermediateStations(List<IntermediateStation> intermediateStations) {
        this.intermediateStations = intermediateStations;
    }
}

