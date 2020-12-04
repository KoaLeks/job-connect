package at.ac.tuwien.sepm.groupphase.backend.util.validator;

import at.ac.tuwien.sepm.groupphase.backend.util.annotation.IsAdult;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateValidator implements ConstraintValidator<IsAdult, LocalDateTime> {
    @Override
    public void initialize(IsAdult constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDateTime birthDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime today = LocalDateTime.now();
        long years = ChronoUnit.YEARS.between(birthDate, today);
        return years >= 18;
    }
}
