package digital.ilia.desafio.validator;

import digital.ilia.desafio.exception.Errors;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class IsDateFormatValidator implements
        ConstraintValidator<digital.ilia.desafio.validator.IsDateFormat, String> {

    private digital.ilia.desafio.validator.IsDateFormat IsDateFormat;

    @Override
    public void initialize(IsDateFormat IsDateFormat) {
        this.IsDateFormat = IsDateFormat;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value== null)
            return true;
        var formato = new SimpleDateFormat ( this.IsDateFormat.pattern());
        try {
            Date data = formato.parse(value);
            return true;
        } catch (ParseException e) {

        }
        return false;
    }

}
