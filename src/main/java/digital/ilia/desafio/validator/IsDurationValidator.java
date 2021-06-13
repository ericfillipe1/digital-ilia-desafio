package digital.ilia.desafio.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class IsDurationValidator implements
        ConstraintValidator<digital.ilia.desafio.validator.IsDuration, String> {

    private digital.ilia.desafio.validator.IsDuration IsDuration;

    @Override
    public void initialize(IsDuration IsDateFormat) {
        this.IsDuration = IsDateFormat;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value== null)
            return true;
        ;
        try {
            var formato = Duration.parse(value);
            return true;
        } catch (DateTimeParseException e) {

        }
        return false;
    }

}
