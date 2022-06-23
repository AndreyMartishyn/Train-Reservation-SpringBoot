package ua.martishyn.app.utils.validation.date_range;

import ua.martishyn.app.models.route.RoutePointDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class DateRangeValidator implements ConstraintValidator<DateRange, RoutePointDTO> {
    @Override
    public void initialize(DateRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RoutePointDTO route, ConstraintValidatorContext context) {
        return LocalDateTime.now().isBefore(route.getDeparture())
                && LocalDateTime.now().isBefore(route.getArrival())
                && route.getDeparture().isAfter(route.getArrival());
    }
}
