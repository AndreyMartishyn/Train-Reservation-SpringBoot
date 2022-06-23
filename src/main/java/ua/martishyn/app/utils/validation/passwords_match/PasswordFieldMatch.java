package ua.martishyn.app.utils.validation.passwords_match;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * PasswordFieldMatch annotation that can be applied for class
 * and contains params field and fieldMatch fields that represent
 * names of field to compare
 */

@Constraint(validatedBy = PasswordMatchValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordFieldMatch {
    String message() default "Password input not match!";

    String field();

    Class<?>[] groups() default {};

    String fieldMatch();

    Class<? extends Payload>[] payload() default {};
}

