package ua.martishyn.app.utils.validation.passwords_match;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * PasswordFieldMatch annotation that can be applied for class
 * and contains params field and fieldMatch fields that represent
 * names of field to compare
 */

public class PasswordMatchValidator implements ConstraintValidator<PasswordFieldMatch, Object> {
    private String field;
    private String fieldMatch;

    @Override
    public void initialize(PasswordFieldMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    /**
     * Method gets 2 field from object. After checking for null
     * check for equality is performed
     *
     * @param value   Object marked with annotation
     * @param context API for adding or disabling additional
     *                error messages
     * @return false if @param is false and values don`t match
     */

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return fieldMatchValue == null;
        }
    }
}
