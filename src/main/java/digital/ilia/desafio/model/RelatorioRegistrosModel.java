package digital.ilia.desafio.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import digital.ilia.desafio.validator.IsDateFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Relatório mensal",name = "relatorioRegistros")
public class RelatorioRegistrosModel {

  @Getter
  @Setter
  @IsDateFormat(message = "Data formato inválido", pattern = "yyyy-MM-dd")
  private String dia ;

  @Getter
  @Setter
  @Schema(example = "[\"08:00:00\", \"12:00:00\", \"13:00:00\", \"18:00:00\"]")
  private List<String> horarios = null;

}
