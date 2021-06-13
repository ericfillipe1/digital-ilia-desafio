package digital.ilia.desafio.exception;

import digital.ilia.desafio.model.MensagemModel;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class Errors {

    public static class Alocacoes {

        public enum Alocar {
            NAO_PODE_ALOCAR_TEMPO_MAIOR_QUE_O_TEMPO_TRABALHADO_NO_DIA(HttpStatus.BAD_REQUEST,"Não pode alocar tempo maior que o tempo trabalhado no dia");
            @Getter
            private GlobalException exception ;
            Alocar( HttpStatus status,String strMensagem) {
                MensagemModel mensagem = new MensagemModel();
                mensagem.setMensagem(strMensagem);
                this.exception = new GlobalException(mensagem,status);
            }

        }
    }
    public static class FolhasDePonto {

        public enum RelatorioMensal {
            RELATORIO_NAO_ENCONTRADO(HttpStatus.NOT_FOUND,"Relatório não encontrado");
            @Getter
            private GlobalException exception ;
            RelatorioMensal( HttpStatus status,String strMensagem) {
                MensagemModel mensagem = new MensagemModel();
                mensagem.setMensagem(strMensagem);
                this.exception = new GlobalException(mensagem,status);
            }

        }
    }


    public static class Batidas {
        public enum BaterPonto {
            HORARIOS_JA_REGISTRADO(HttpStatus.CONFLICT,"Horários já registrado"),
            APENAS_4_HORARIOS_PODEM_SER_REGISTRADOS_POR_DIA(HttpStatus.FORBIDDEN,"Apenas 4 horários podem ser registrados por dia"),
            DEVE_HAVER_NO_MÍNIMO_1_HORA_DE_ALMOCO(HttpStatus.FORBIDDEN,"Deve haver no mínimo 1 hora de almoço"),
            SABADO_E_DOMINGO_NAO_SAO_PERMITIDOS_COMO_DIA_DE_TRABALHO(HttpStatus.FORBIDDEN,"Sábado e domingo não são permitidos como dia de trabalho");

            @Getter
            private GlobalException exception ;
            BaterPonto( HttpStatus status,String strMensagem) {
                MensagemModel mensagem = new MensagemModel();
                mensagem.setMensagem(strMensagem);
                this.exception = new GlobalException(mensagem,status);
            }

        }

        }

}
