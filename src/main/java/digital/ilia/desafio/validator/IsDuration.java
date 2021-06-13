package digital.ilia.desafio.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsDurationValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsDuration {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}