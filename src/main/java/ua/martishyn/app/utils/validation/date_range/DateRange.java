package ua.martishyn.app.utils.validation.date_range;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateRangeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface DateRange {
    String message() default "Wrong dates";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


