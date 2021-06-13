/*
 *   Copyright (c) 2021 
 *   All rights reserved.
 */
package digital.ilia.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import digital.ilia.desafio.validator.IsDateFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * O momento da batida
 **/
@Schema(description = "O momento da batida", name = "momento")
@NoArgsConstructor
public class MomentoModel {
    /**
     * Data e hora da batida
     **/
    @Schema(example = "2018-08-22T08:00:00", description = "Data e hora da batida")
    @Setter
    @Getter
    @NotNull(message = "Campo obrigatório não informado")
    @IsDateFormat(message = "Data e hora em formato inválido", pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String dataHora = null;
    public MomentoModel(String dataHora){
        this.dataHora = dataHora;
    }

}
