package digital.ilia.desafio.validator;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsDateFormatValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsDateFormat  {
    String message();
    String pattern();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}