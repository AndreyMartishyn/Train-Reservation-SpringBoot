package ua.martishyn.app.models;

public class PersonalRoute  {
    private int routeId;
    private int trainId;
    private String departure;
    private String departureStation;
    private String arrival;
    private String arrivalStation;
    private String roadTime;
    private int firstClassSeats;
    private int secondClassSeats;
    private int firstClassTotalPrice;
    private int secondClassTotalPrice;
    private StringBuilder redirectLink;


    public int getRouteId() {
        return routeId;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public String getRoadTime() {
        return roadTime;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }


    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public void setRoadTime(String roadTime) {
        this.roadTime = roadTime;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public int getFirstClassSeats() {
        return firstClassSeats;
    }

    public void setFirstClassSeats(int firstClassSeats) {
        this.firstClassSeats = firstClassSeats;
    }

    public int getSecondClassSeats() {
        return secondClassSeats;
    }

    public void setSecondClassSeats(int secondClassSeats) {
        this.secondClassSeats = secondClassSeats;
    }

    public StringBuilder getRedirectLink() {
        return redirectLink;
    }

    public void setRedirectLink(StringBuilder redirectLink) {
        this.redirectLink = redirectLink;
    }

    public int getFirstClassTotalPrice() {
        return firstClassTotalPrice;
    }

    public void setFirstClassTotalPrice(int firstClassTotalPrice) {
        this.firstClassTotalPrice = firstClassTotalPrice;
    }

    public int getSecondClassTotalPrice() {
        return secondClassTotalPrice;
    }

    public void setSecondClassTotalPrice(int secondClassTotalPrice) {
        this.secondClassTotalPrice = secondClassTotalPrice;
    }
}
