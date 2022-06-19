package ua.martishyn.app.models.booking;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchingRouteSeatsDetails {
        private int firstClassSeats;
        private int secondClassSeats;
        private int firstClassTotalPrice;
        private int secondClassTotalPrice;
}
