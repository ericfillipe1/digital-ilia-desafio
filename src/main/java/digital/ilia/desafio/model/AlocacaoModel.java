/*
 *   Copyright (c) 2021 
 *   All rights reserved.
 */
package digital.ilia.desafio.model;

import digital.ilia.desafio.validator.IsDateFormat;
import digital.ilia.desafio.validator.IsDuration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Alocação
 **/

@NoArgsConstructor
@Schema(description = "Alocação",name = "alocacao")
public class AlocacaoModel {
  @Getter
  @Setter
  @IsDateFormat(message = "Data formato inválido", pattern = "yyyy-MM-dd")
  private String dia ;

  @Getter
  @Setter
  @IsDuration(message = "Tempo inválido")
  @Schema(example = "PT2H30M0S")
  private String tempo = null;

  @Getter
  @Setter
  @Schema(example = "ACME Corporation")
  private String nomeProjeto = null;


  public AlocacaoModel(String dia ,  String tempo,String nomeProjeto) {
    this.dia = dia;
    this.nomeProjeto = nomeProjeto;
    this.tempo = tempo;
  }
}
