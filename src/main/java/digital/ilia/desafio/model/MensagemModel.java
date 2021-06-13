package digital.ilia.desafio.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Schema(name = "mensagem")
public class MensagemModel {
  /**
   * mensagem
   **/
  @Getter
  @Setter
  @JsonProperty("mensagem")
  private String mensagem = null;

  public MensagemModel(String mensagem) {
    this.mensagem=mensagem;
  }
}
