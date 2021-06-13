package digital.ilia.desafio.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Relatório mensal
 **/
@Schema(description = "Relatório mensal",name = "relatorio")
public class RelatorioModel {

  @Schema(example = "2018-08")
  @Getter
  @Setter
  private String mes = null;

  @Schema(example = "PT69H35M5S")
  @Getter
  @Setter
  private String horasTrabalhadas = null;

  @Schema(example = "PT25M5S")
  @Getter
  @Setter
  private String horasExcedentes = null;

  @Schema(example = "PT0S")
  @Getter
  @Setter
  private String horasDevidas = null;

  @Getter
  @Setter
  private List<RelatorioRegistrosModel> registros = null;

  @Getter
  @Setter
  private List<RelatorioAlocacoesModel> alocacoes = null;
}
