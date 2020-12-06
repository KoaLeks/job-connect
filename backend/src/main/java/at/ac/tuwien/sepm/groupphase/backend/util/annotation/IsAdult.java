package at.ac.tuwien.sepm.groupphase.backend.util.annotation;

import at.ac.tuwien.sepm.groupphase.backend.util.validator.DateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsAdult {
    String message() default "Invalid birth date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
