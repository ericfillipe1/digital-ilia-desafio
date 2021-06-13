package digital.ilia.desafio.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "relatorioAlocacoes")
public class RelatorioAlocacoesModel {

  @Getter
  @Setter
  @Schema(example = "ACME Corporation")
  private String nomeProjeto ;
  //https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html
  @Getter
  @Setter
  @Schema(example = "PT69H35M5S")
  private String tempo ;
}
